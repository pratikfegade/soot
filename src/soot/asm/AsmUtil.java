/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-2014 Raja Vallee-Rai and others
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
package soot.asm;

import soot.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains static utility methods.
 *
 * @author Aaloan Miftah
 */
/**
 * @author eric
 *
 */
class AsmUtil {

	/**
	 * Determines if a type is a dword type.
	 * @param type the type to check.
	 * @return {@code true} if its a dword type.
	 */
	static boolean isDWord(Type type) {
		return type instanceof LongType || type instanceof DoubleType;
	}

	/**
	 * Converts an internal class name to a Type.
	 * @param internal internal name.
	 * @return type
	 */
	static Type toBaseType(String internal) {
		if (internal.charAt(0) == '['){ 
			/* [Ljava/lang/Object; */
			internal = internal.substring(internal.lastIndexOf('[')+1, internal.length());
			/* Ljava/lang/Object */
		}
		if(internal.charAt(internal.length()-1)==';') {
			internal = internal.substring(0, internal.length()-1);
			// we need to have this guarded by a ; check as you can have a situation
			// were a call is called Lxxxxx with now leading package name. Rare, but it
			// happens. However, you need to strip the leading L it will always be
			// followed by a ; per
			// http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
			if (internal.charAt(0) == 'L') {
				internal = internal.substring(1, internal.length());
			}
			internal = toQualifiedName(internal);
			return RefType.getInstance(internal);
		}
		switch (internal.charAt(0)) {
			case 'Z':
				return BooleanType.getInstance();
			case 'B':
				return ByteType.getInstance();
			case 'C':
				return CharType.getInstance();
			case 'S':
				return ShortType.getInstance();
			case 'I':
				return IntType.getInstance();
			case 'F':
				return FloatType.getInstance();
			case 'J':
				return LongType.getInstance();
			case 'D':
				return DoubleType.getInstance();
			default:
				internal = toQualifiedName(internal);
				return RefType.getInstance(internal);
		}
	}

	/**
	 * Converts an internal class name to a fully qualified name.
	 * @param internal internal name.
	 * @return fully qualified name.
	 */
	static String toQualifiedName(String internal) {
		return internal.replace('/', '.');
	}

	/**
	 * Converts a fully qualified class name to an internal name.
	 * @param qual fully qualified class name.
	 * @return internal name.
	 */
	private static String toInternalName(String qual) {
		return qual.replace('.', '/');
	}

	/**
	 * Determines and returns the internal name of a class.
	 * @param cls the class.
	 * @return corresponding internal name.
	 */
	public static String toInternalName(SootClass cls) {
		return toInternalName(cls.getName());
	}

	/**
	 * Converts a type descriptor to a Jimple reference type.
	 * @param desc the descriptor.
	 * @return the reference type.
	 */
	static Type toJimpleRefType(String desc) {
		return desc.charAt(0) == '[' ?
				toJimpleType(desc) : RefType.getInstance(toQualifiedName(desc));
	}

	/**
	 * Converts a type descriptor to a Jimple type.
	 * @param desc the descriptor.
	 * @return equivalent Jimple type.
	 */
	static Type toJimpleType(String desc) {
		int idx = desc.lastIndexOf('[');
		int nrDims = idx + 1;
		if (nrDims > 0) {
			if (desc.charAt(0) != '[')
				throw new AssertionError("Invalid array descriptor: " + desc);
			desc = desc.substring(idx + 1);
		}
		Type baseType;
		switch (desc.charAt(0)) {
			case 'Z':
				baseType = BooleanType.getInstance();
				break;
			case 'B':
				baseType = ByteType.getInstance();
				break;
			case 'C':
				baseType = CharType.getInstance();
				break;
			case 'S':
				baseType = ShortType.getInstance();
				break;
			case 'I':
				baseType = IntType.getInstance();
				break;
			case 'F':
				baseType = FloatType.getInstance();
				break;
			case 'J':
				baseType = LongType.getInstance();
				break;
			case 'D':
				baseType = DoubleType.getInstance();
				break;
			case 'L':
				if (desc.charAt(desc.length() - 1) != ';')
					throw new AssertionError("Invalid reference descriptor: " + desc);
				String name = desc.substring(1, desc.length() - 1);
				name = toQualifiedName(name);
				baseType = RefType.getInstance(name);
				break;
			default:
				throw new AssertionError("Unknown descriptor: " + desc);
		}
		if (!(baseType instanceof RefLikeType) && desc.length() > 1)
			throw new AssertionError("Invalid primitive type descriptor: " + desc);
		return nrDims > 0 ? ArrayType.getInstance(baseType, nrDims) : baseType;
	}

	/**
	 * Converts a method signature to a list of types, with the last entry
	 * in the returned list denoting the return type.
	 * @param desc method signature.
	 * @return list of types.
	 */
	static List<Type> toJimpleDesc(String desc) {
		ArrayList<Type> types = new ArrayList<>(2);
		int len = desc.length();
		int idx = 0;
		all:
		while (idx != len) {
			int nrDims = 0;
			Type baseType = null;
			this_type:
			while (idx != len) {
				char c = desc.charAt(idx++);
				switch (c) {
					case '(':
					case ')':
						continue all;
					case '[':
						++nrDims;
						continue this_type;
					case 'Z':
						baseType = BooleanType.getInstance();
						break this_type;
					case 'B':
						baseType = ByteType.getInstance();
						break this_type;
					case 'C':
						baseType = CharType.getInstance();
						break this_type;
					case 'S':
						baseType = ShortType.getInstance();
						break this_type;
					case 'I':
						baseType = IntType.getInstance();
						break this_type;
					case 'F':
						baseType = FloatType.getInstance();
						break this_type;
					case 'J':
						baseType = LongType.getInstance();
						break this_type;
					case 'D':
						baseType = DoubleType.getInstance();
						break this_type;
					case 'V':
						baseType = new VoidType();
						break this_type;
					case 'L':
						int begin = idx;
						while (desc.charAt(++idx) != ';');
						String cls = desc.substring(begin, idx++);
						baseType = RefType.getInstance(toQualifiedName(cls));
						break this_type;
					default:
						throw new AssertionError("Unknown type: " + c);
				}
			}
			if (baseType != null && nrDims > 0)
				types.add(ArrayType.getInstance(baseType, nrDims));
			else
				types.add(baseType);
		}
		return types;
	}

	/**
	 * strips suffix for indicating an array type
	 */
	static String baseTypeName(String s) {
		int index = s.indexOf("[");
		if(index<0) {
			return s;
		} else {
			return s.substring(0,index);
		}
	}

	private AsmUtil() {
	}
}
