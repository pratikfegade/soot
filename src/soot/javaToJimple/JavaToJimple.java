/* Soot - a J*va Optimization Framework
 * Copyright (C) 2004 Jennifer Lhotak
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

package soot.javaToJimple;

import polyglot.ast.Node;
import polyglot.frontend.*;
import polyglot.frontend.goals.VisitorGoal;
import polyglot.visit.NodeVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JavaToJimple {
	
//    public static final polyglot.frontend.goals.VisitorGoal CAST_INSERTION = new polyglot.frontend.goals.VisitorGoal("cast-insertion");
//    public static final polyglot.frontend.Pass.ID STRICTFP_PROP = new polyglot.frontend.goals.Goal("strictfp-prop");
//    public static final polyglot.frontend.Pass.ID ANON_CONSTR_FINDER = new polyglot.frontend.passes.("anon-constr-finder");
//    public static final polyglot.frontend.Pass.ID SAVE_AST = new polyglot.frontend.Pass.ID("save-ast");
    
    /**
     * sets up the info needed to invoke polyglot
     */
	public ExtensionInfo initExtInfo(String fileName, List<String> sourceLocations){
		
        Set<String> source = new HashSet<>();
        ExtensionInfo extInfo = new soot.javaToJimple.jj.ExtensionInfo() {
            public List passes(Job job) {
                List passes = super.passes(job);
                //beforePass(passes, Pass.EXIT_CHECK, new VisitorPass(polyglot.frontend.Pass.FOLD, job, new polyglot.visit.ConstantFolder(ts, nf)));
                NodeVisitor civ = new CastInsertionVisitor(job, ts, nf);
                NodeVisitor strictfp_prop = new StrictFPPropagator(false);
                NodeVisitor anon_constr_finder = new AnonConstructorFinder(job, ts, nf);
                NodeVisitor save_ast = new SaveASTVisitor(new VisitorGoal(job,), ts, nf);
//                beforePass(passes, Pass.EXIT_CHECK, new VisitorPass(CAST_INSERTION, job, new CastInsertionVisitor(job, ts, nf)));
//                beforePass(passes, Pass.EXIT_CHECK, new VisitorPass(STRICTFP_PROP, job, new StrictFPPropagator(false)));
//                beforePass(passes, Pass.EXIT_CHECK, new VisitorPass(ANON_CONSTR_FINDER, job, new AnonConstructorFinder(job, ts, nf)));
//                afterPass(passes, Pass.PRE_OUTPUT_ALL, new SaveASTVisitor(SAVE_AST, job, this));
//                removePass(passes, Pass.OUTPUT);
                return passes;
            }
            
        };
        polyglot.main.Options options = extInfo.getOptions();

        options.assertions = true;
        options.source_path = new LinkedList<File>();
        Iterator<String> it = sourceLocations.iterator();
        while (it.hasNext()){
            Object next = it.next();
            //System.out.println("adding src loc: "+next.toString());
            options.source_path.add(new File(next.toString()));
        }

        options.source_ext = new String []{"java"};
		options.serialize_type_info = false;
		
		source.add(fileName);
		
		options.source_path.add(new File(fileName).getParentFile());
		
        polyglot.main.Options.global = options;

        return extInfo;
    }
	
    /**
     * uses polyglot to compile source and build AST
     */
    public Node compile(polyglot.frontend.Compiler compiler, String fileName, ExtensionInfo extInfo){
		SourceLoader source_loader = compiler.sourceExtension().sourceLoader();

		try {
            FileSource source = new FileSource(new File(fileName));

            Job job = null;
            if (compiler.sourceExtension() instanceof soot.javaToJimple.jj.ExtensionInfo){
                soot.javaToJimple.jj.ExtensionInfo jjInfo = (soot.javaToJimple.jj.ExtensionInfo)compiler.sourceExtension();
                if (jjInfo.sourceJobMap() != null){
                    job = jjInfo.sourceJobMap().get(source);
                }
            }
            if (job == null){
			    job = compiler.sourceExtension().compiler().r;
            }
   
            boolean result = false;
		    result = compiler.sourceExtension().;
		
            if (!result) {
            
                throw new soot.CompilationDeathException(0, "Could not compile");
            }
            
            Node node = job.ast();

			return node;

		}
		catch (IOException e){
            return null;
		}

	}

}
