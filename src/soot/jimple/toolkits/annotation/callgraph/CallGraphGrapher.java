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

package soot.jimple.toolkits.annotation.callgraph;

import soot.*;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.CGGOptions;
import soot.options.Options;
import soot.toolkits.graph.interaction.InteractionHandler;

import java.util.ArrayList;
import java.util.Map;

/** A scene transformer that creates a graphical callgraph. */
public class CallGraphGrapher extends SceneTransformer
{ 
    public CallGraphGrapher(Singletons.Global g){}
    public static CallGraphGrapher v() { return G.v().soot_jimple_toolkits_annotation_callgraph_CallGraphGrapher();}

    private MethodToContexts methodToContexts;
    private CallGraph cg;
    private boolean showLibMeths;

    protected void internalTransform(String phaseName, Map options){
        
        CGGOptions opts = new CGGOptions(options);
        if (opts.show_lib_meths()){
            setShowLibMeths(true);
        }
        cg = Scene.v().getCallGraph();
        if (Options.v().interactive_mode()){
            reset();
        }
    }

    public void reset() {
        if (methodToContexts == null){
            methodToContexts = new MethodToContexts(Scene.v().getReachableMethods().listener());
        }
        
        if(Scene.v().hasCallGraph()) {
	        SootClass sc = Scene.v().getMainClass();
	        SootMethod sm = getFirstMethod(sc);
	        //G.v().out.println("got first method");
            //G.v().out.println("got tgt methods");
            //G.v().out.println("got src methods");
	        CallGraphInfo info = new CallGraphInfo();
	        //G.v().out.println("will handle new call graph");
	        InteractionHandler.v().handleCallGraphStart(info, this);
        }
    }

    private SootMethod getFirstMethod(SootClass sc){
        ArrayList paramTypes = new ArrayList();
        paramTypes.add(soot.ArrayType.v(soot.RefType.v("java.lang.String"), 1));
        SootMethod sm = sc.getMethodUnsafe("main", paramTypes, soot.VoidType.v());
        if (sm != null) {
            return sm;
        }
        else {
            return sc.getMethods().get(0);
        }
    }
    
    public void handleNextMethod(){
        if (!getNextMethod().hasActiveBody()) return;
        //System.out.println("for: "+getNextMethod().getName()+" tgts: "+tgts);
        //System.out.println("for: "+getNextMethod().getName()+" srcs: "+srcs);
        CallGraphInfo info = new CallGraphInfo();
        //System.out.println("sending next method");
        InteractionHandler.v().handleCallGraphPart(info);
        //handleNextMethod();
    }
    
    private SootMethod nextMethod;

    public void setNextMethod(SootMethod m){
        nextMethod = m;
    }

    public SootMethod getNextMethod(){
        return nextMethod;
    }

    public void setShowLibMeths(boolean b){
        showLibMeths = b;
    }

}


