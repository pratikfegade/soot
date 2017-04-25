/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-2000 Etienne Gagnon.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package soot.jimple.toolkits.typing.integer;

import soot.*;
import soot.jimple.JimpleBody;
import soot.jimple.Stmt;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class resolves the type of local variables.
 **/
public class TypeResolver {
	/** All type variable instances **/
	private final List<TypeVariable> typeVariableList = new ArrayList<>();

	/** Hashtable: [TypeNode or Local] -> TypeVariable **/
	private final Map<Object, TypeVariable> typeVariableMap = new ConcurrentHashMap<>();

	private final JimpleBody stmtBody;

	final TypeVariable BOOLEAN = typeVariable(ClassHierarchy.getInstance().BOOLEAN);
	final TypeVariable BYTE = typeVariable(ClassHierarchy.getInstance().BYTE);
	final TypeVariable SHORT = typeVariable(ClassHierarchy.getInstance().SHORT);
	final TypeVariable CHAR = typeVariable(ClassHierarchy.getInstance().CHAR);
	final TypeVariable INT = typeVariable(ClassHierarchy.getInstance().INT);
	final TypeVariable TOP = typeVariable(ClassHierarchy.getInstance().TOP);
	final TypeVariable R0_1 = typeVariable(ClassHierarchy.getInstance().R0_1);
	final TypeVariable R0_127 = typeVariable(ClassHierarchy.getInstance().R0_127);
	final TypeVariable R0_32767 = typeVariable(ClassHierarchy.getInstance().R0_32767);
	final TypeVariable LONG = typeVariable(ClassHierarchy.getInstance().LONG);
	private static final boolean DEBUG = false;

	// categories for type variables (solved = hard, unsolved = soft)
	private List<TypeVariable> unsolved;
	private List<TypeVariable> solved;

	/** Get type variable for the given local. **/
	TypeVariable typeVariable(Local local) {
		TypeVariable result = typeVariableMap.get(local);

		if (result == null) {
			int id = typeVariableList.size();
			typeVariableList.add(null);

			result = new TypeVariable(id, this);

			typeVariableList.set(id, result);
			typeVariableMap.put(local, result);

			if (DEBUG) {
				System.out.println("[LOCAL VARIABLE \"" + local + "\" -> " + id
						+ "]");
			}
		}

		return result;
	}

	/** Get type variable for the given type node. **/
	private TypeVariable typeVariable(TypeNode typeNode) {
		TypeVariable result = typeVariableMap.get(typeNode);

		if (result == null) {
			int id = typeVariableList.size();
			typeVariableList.add(null);

			result = new TypeVariable(id, this, typeNode);

			typeVariableList.set(id, result);
			typeVariableMap.put(typeNode, result);
		}

		return result;
	}

	/** Get type variable for the given type. **/
	TypeVariable typeVariable(Type type) {
		return typeVariable(ClassHierarchy.getInstance().typeNode(type));
	}

	/** Get new type variable **/
	TypeVariable typeVariable() {
		int id = typeVariableList.size();
		typeVariableList.add(null);

		TypeVariable result = new TypeVariable(id, this);

		typeVariableList.set(id, result);

		return result;
	}

	private TypeResolver(JimpleBody stmtBody) {
		this.stmtBody = stmtBody;
	}

	public static void resolve(JimpleBody stmtBody) {
		if (DEBUG) {
			System.out.println(stmtBody.getMethod());
		}

		try {
			TypeResolver resolver = new TypeResolver(stmtBody);
			resolver.resolve_step_1();
		} catch (TypeException e1) {
			if (DEBUG) {
				System.out.println("[integer] Step 1 Exception-->"
						+ e1.getMessage());
			}

			try {
				TypeResolver resolver = new TypeResolver(stmtBody);
				resolver.resolve_step_2();
			} catch (TypeException e2) {
				StringWriter st = new StringWriter();
				PrintWriter pw = new PrintWriter(st);
				e2.printStackTrace(pw);
				pw.close();
				throw new RuntimeException(st.toString());
			}
		}
	}

	private void debug_vars(String message) {
		if (DEBUG) {
			int count = 0;
			System.out.println("**** START:" + message);
			for (TypeVariable var : typeVariableList) {
				System.out.println(count++ + " " + var);
			}
			System.out.println("**** END:" + message);
		}
	}

	private void resolve_step_1() throws TypeException {
		collect_constraints_1();
		debug_vars("constraints");

		compute_approximate_types();
		merge_connected_components();
		debug_vars("components");

		merge_single_constraints();
		debug_vars("single");

		assign_types_1();
		debug_vars("assign");

		try {
			check_constraints();
		} catch (TypeException e) {
			if (DEBUG) {
				System.out.println("[integer] Step 1(check) Exception ["
						+ stmtBody.getMethod() + "]-->" + e.getMessage());
			}

			check_and_fix_constraints();
		}
	}

	private void resolve_step_2() throws TypeException {
		collect_constraints_2();
		compute_approximate_types();
		assign_types_2();
		check_and_fix_constraints();
	}

	private void collect_constraints_1() {
		ConstraintCollector collector = new ConstraintCollector(this, true);

		for (Unit u : stmtBody.getUnits()) {
			final Stmt stmt = (Stmt) u;
			if (DEBUG) {
				System.out.print("stmt: ");
			}
			collector.collect(stmt, stmtBody);
			if (DEBUG) {
				System.out.println(stmt);
			}
		}
	}

	private void collect_constraints_2() {
		ConstraintCollector collector = new ConstraintCollector(this, false);

		for (Unit u : stmtBody.getUnits()) {
			final Stmt stmt = (Stmt) u;
			if (DEBUG) {
				System.out.print("stmt: ");
			}
			collector.collect(stmt, stmtBody);
			if (DEBUG) {
				System.out.println(stmt);
			}
		}
	}

	private void merge_connected_components() throws TypeException {
		compute_solved();
		List<TypeVariable> list = new LinkedList<TypeVariable>();
		list.addAll(solved);
		list.addAll(unsolved);

		StronglyConnectedComponents.merge(list);
	}

	private void merge_single_constraints() throws TypeException {
		boolean modified = true;

		while (modified) {
			modified = false;
			refresh_solved();

			for (TypeVariable var : unsolved) {

				List<TypeVariable> children_to_remove = new LinkedList<TypeVariable>();
				TypeNode lca = null;

				var.fixChildren();

				for (TypeVariable child : var.children()) {

					TypeNode type = child.type();

					if (type != null) {
						children_to_remove.add(child);

						if (lca == null) {
							lca = type;
						} else {
							lca = lca.lca_1(type);
						}
					}
				}

				if (lca != null) {
					if (DEBUG) {
						if (lca == ClassHierarchy.getInstance().TOP) {
							System.out.println("*** TOP *** " + var);
							for (TypeVariable typeVariable : children_to_remove) {
								System.out.println("-- " + typeVariable);
							}
						}
					}

					for (TypeVariable child : children_to_remove) {

						var.removeChild(child);
					}

					var.addChild(typeVariable(lca));
				}

				if (var.children().size() == 1) {
					TypeVariable child = var.children().get(0);
					TypeNode type = child.type();

					if (type == null || type.type() != null) {
						var.union(child);
						modified = true;
					}
				}
			}

			if (!modified) {
				for (TypeVariable var : unsolved) {
					List<TypeVariable> parents_to_remove = new LinkedList<TypeVariable>();
					TypeNode gcd = null;

					var.fixParents();

					for (TypeVariable parent : var.parents()) {

						TypeNode type = parent.type();

						if (type != null) {
							parents_to_remove.add(parent);

							if (gcd == null) {
								gcd = type;
							} else {
								gcd = gcd.gcd_1(type);
							}
						}
					}

					if (gcd != null) {
						for (TypeVariable parent : parents_to_remove) {
							var.removeParent(parent);
						}

						var.addParent(typeVariable(gcd));
					}

					if (var.parents().size() == 1) {
						TypeVariable parent = var.parents().get(0);
						TypeNode type = parent.type();

						if (type == null || type.type() != null) {
							var.union(parent);
							modified = true;
						}
					}
				}
			}

			if (!modified) {
				for (TypeVariable var : unsolved) {
					if (var.type() == null && var.inv_approx() != null
							&& var.inv_approx().type() != null) {
						if (DEBUG) {
							System.out.println("*** I->"
									+ var.inv_approx().type() + " *** " + var);
						}

						var.union(typeVariable(var.inv_approx()));
						modified = true;
					}
				}
			}

			if (!modified) {
				for (TypeVariable var : unsolved) {
					if (var.type() == null && var.approx() != null
							&& var.approx().type() != null) {
						if (DEBUG) {
							System.out.println("*** A->" + var.approx().type()
									+ " *** " + var);
						}

						var.union(typeVariable(var.approx()));
						modified = true;
					}
				}
			}

			if (!modified) {
				for (TypeVariable var : unsolved) {
					if (var.type() == null
							&& var.approx() == ClassHierarchy.getInstance().R0_32767) {
						if (DEBUG) {
							System.out.println("*** R->SHORT *** " + var);
						}

						var.union(SHORT);
						modified = true;
					}
				}
			}

			if (!modified) {
				for (TypeVariable var : unsolved) {
					if (var.type() == null
							&& var.approx() == ClassHierarchy.getInstance().R0_127) {
						if (DEBUG) {
							System.out.println("*** R->BYTE *** " + var);
						}

						var.union(BYTE);
						modified = true;
					}
				}
			}

			if (!modified) {
				for (TypeVariable var : R0_1.parents()) {
					if (var.type() == null
							&& var.approx() == ClassHierarchy.getInstance().R0_1) {
						if (DEBUG) {
							System.out.println("*** R->BOOLEAN *** " + var);
						}
						var.union(BOOLEAN);
						modified = true;
					}
				}
			}
		}
	}

	private void assign_types_1() throws TypeException {
		for (Iterator<Local> localIt = stmtBody.getLocals().iterator(); localIt
				.hasNext();) {
			final Local local = localIt.next();

			if (local.getType() instanceof IntegerType) {
				TypeVariable var = typeVariable(local);

				if (var.type() == null || var.type().type() == null) {
					TypeVariable
							.error("Type Error(21):  Variable without type");
				} else {
					local.setType(var.type().type());
				}

				if (DEBUG) {
					if ((var != null) && (var.approx() != null)
							&& (var.approx().type() != null) && (local != null)
							&& (local.getType() != null)
							&& !local.getType().equals(var.approx().type())) {
						System.out.println("local: " + local + ", type: "
								+ local.getType() + ", approx: "
								+ var.approx().type());
					}
				}
			}
		}
	}

	private void assign_types_2() throws TypeException {
		for (Iterator<Local> localIt = stmtBody.getLocals().iterator(); localIt
				.hasNext();) {
			final Local local = localIt.next();

			if (local.getType() instanceof IntegerType) {
				TypeVariable var = typeVariable(local);

				if (var.inv_approx() != null && var.inv_approx().type() != null) {
					local.setType(var.inv_approx().type());
				} else if (var.approx().type() != null) {
					local.setType(var.approx().type());
				} else if (var.approx() == ClassHierarchy.getInstance().R0_1) {
					local.setType(BooleanType.getInstance());
				} else if (var.approx() == ClassHierarchy.getInstance().R0_127) {
					local.setType(ByteType.getInstance());
				} else {
					local.setType(ShortType.getInstance());
				}
			}
		}
	}

	private void check_constraints() throws TypeException {
		ConstraintChecker checker = new ConstraintChecker(this, false);
		StringBuffer s = null;

		if (DEBUG) {
			s = new StringBuffer("Checking:\n");
		}

		for (Iterator<Unit> stmtIt = stmtBody.getUnits().iterator(); stmtIt
				.hasNext();) {

			final Stmt stmt = (Stmt) stmtIt.next();
			if (DEBUG) {
				s.append(" " + stmt + "\n");
			}
			try {
				checker.check(stmt, stmtBody);
			} catch (TypeException e) {
				if (DEBUG) {
					System.out.println(s);
				}
				throw e;
			}
		}
	}

	private void check_and_fix_constraints() throws TypeException {
		ConstraintChecker checker = new ConstraintChecker(this, true);
		StringBuffer s = null;
		PatchingChain<Unit> units = stmtBody.getUnits();
		Stmt[] stmts = new Stmt[units.size()];
		units.toArray(stmts);

		if (DEBUG) {
			s = new StringBuffer("Checking:\n");
		}

		for (Stmt stmt : stmts) {
			if (DEBUG) {
				s.append(" " + stmt + "\n");
			}
			try {
				checker.check(stmt, stmtBody);
			} catch (TypeException e) {
				if (DEBUG) {
					System.out.println(s);
				}
				throw e;
			}
		}
	}

	private void compute_approximate_types() throws TypeException {
		TreeSet<TypeVariable> workList = new TreeSet<TypeVariable>();

		for (TypeVariable var : typeVariableList) {

			if (var.type() != null) {
				workList.add(var);
			}
		}

		TypeVariable.computeApprox(workList);

		workList = new TreeSet<TypeVariable>();

		for (TypeVariable var : typeVariableList) {

			if (var.type() != null) {
				workList.add(var);
			}
		}

		TypeVariable.computeInvApprox(workList);

		for (TypeVariable var : typeVariableList) {

			if (var.approx() == null) {
				var.union(INT);
			}
		}
	}

	private void compute_solved() {
		Set<TypeVariable> unsolved_set = new TreeSet<TypeVariable>();
		Set<TypeVariable> solved_set = new TreeSet<TypeVariable>();

		for (TypeVariable var : typeVariableList) {

			if (var.type() == null) {
				unsolved_set.add(var);
			} else {
				solved_set.add(var);
			}
		}

		solved = new LinkedList<TypeVariable>(solved_set);
		unsolved = new LinkedList<TypeVariable>(unsolved_set);
	}

	private void refresh_solved() throws TypeException {
		Set<TypeVariable> unsolved_set = new TreeSet<TypeVariable>();
		Set<TypeVariable> solved_set = new TreeSet<TypeVariable>(solved);

		for (TypeVariable var : unsolved) {

			if (var.type() == null) {
				unsolved_set.add(var);
			} else {
				solved_set.add(var);
			}
		}

		solved = new LinkedList<TypeVariable>(solved_set);
		unsolved = new LinkedList<TypeVariable>(unsolved_set);
	}
}
