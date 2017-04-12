/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Ondrej Lhotak
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

package soot;

import soot.coffi.Utf8_Enumeration;
import soot.dexpler.DalvikThrowAnalysis;
import soot.jimple.toolkits.typing.ClassHierarchy;
import soot.singletons.Singletons;

import java.io.PrintStream;
import java.util.*;

/** A class to group together all the global variables in Soot. */
public class G extends Singletons
{
    
    public interface GlobalObjectGetter {
    	G getG();
    	void reset();
    }
    
    public static G v() { return objectGetter.getG(); }
    public static void reset() { objectGetter.reset(); }
    
    private static GlobalObjectGetter objectGetter = new GlobalObjectGetter() {

        private G instance = new G();
        
		@Override
		public G getG() {
			return instance;
		}

		@Override
		public void reset() {
			instance = new G();
		}
	};
	
	public static void setGlobalObjectGetter(GlobalObjectGetter newGetter) {
		objectGetter = newGetter;
	}

    public PrintStream out = System.out;

    public class Global {
    }

    public List<Timer> Timer_outstandingTimers = new ArrayList<Timer>();
    public boolean Timer_isGarbageCollecting;
    public Timer Timer_forcedGarbageCollectionTimer = new Timer("gc");
    public int Timer_count;
    public final Map<Scene, ClassHierarchy> ClassHierarchy_classHierarchyMap = new HashMap<Scene, ClassHierarchy>();
    public final Map<MethodContext, MethodContext> MethodContext_map = new HashMap<MethodContext, MethodContext>();
    
    public DalvikThrowAnalysis interproceduralDalvikThrowAnalysis = null;
    public DalvikThrowAnalysis interproceduralDalvikThrowAnalysis() {
    	if (this.interproceduralDalvikThrowAnalysis == null)
    		this.interproceduralDalvikThrowAnalysis = new DalvikThrowAnalysis(g, true);
    	return this.interproceduralDalvikThrowAnalysis;
    }


    /*
     * Nomair A. Naeem January 15th 2006
     * Added For Dava.toolkits.AST.transformations.SuperFirstStmtHandler
     *
     * The SootMethodAddedByDava is checked by the PackManager after
     * decompiling methods for a class. If any additional methods
     * were added by the decompiler (refer to filer SuperFirstStmtHandler)
     * SootMethodsAdded ArrayList contains these method. These
     * methods are then added to the SootClass
     * 
     * Some of these newly added methods make use of an object of 
     * a static inner class DavaSuperHandler which is to be output 
     * in the decompilers
     * output. The class is marked to need a DavaSuperHandlerClass
     * by adding it into the SootClassNeedsDavaSuperHandlerClass list.
     * The DavaPrinter when printing out the class checks this list and
     * if this class's name exists in the list prints out an implementation
     * of DavSuperHandler
     */
    public boolean SootMethodAddedByDava;

    //ASTMetrics Data
    public Utf8_Enumeration coffi_CONSTANT_Utf8_info_e1 = new Utf8_Enumeration();
    public Utf8_Enumeration coffi_CONSTANT_Utf8_info_e2 = new Utf8_Enumeration();

}

