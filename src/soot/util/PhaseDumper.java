/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 John Jorgensen
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
 * Modified by the Sable Research Group and others 1997-2003.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */


package soot.util;

import soot.*;
import soot.options.Options;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalGraph;
import soot.util.cfgcmd.CFGToDotGraph;
import soot.util.dot.DotGraph;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * The <tt>PhaseDumper</tt> is a debugging aid.  It maintains two
 * lists of phases to be debugged.  If a phase is on the
 * <code>bodyDumpingPhases</code> list, then the intermediate
 * representation of the bodies being manipulated by the phase is
 * dumped before and after the phase is applied.  If a phase is on the
 * <code>cfgDumpingPhases</code> list, then whenever a CFG is
 * constructed during the phase, a dot file is dumped representing the
 * CFG constructed.
 */

public class PhaseDumper {
	private List cfgDumpingPhases = null;

	private class PhaseStack extends ArrayList {
		// We eschew java.util.Stack to avoid synchronization overhead.

		private final static int initialCapacity = 4;
		final static String EMPTY_STACK_PHASE_NAME = "NOPHASE";

		PhaseStack() {
			super(initialCapacity);
		}

		boolean empty() {
			return (this.size() == 0);
		}

		String currentPhase() {
			if (this.size() <= 0) {
				return EMPTY_STACK_PHASE_NAME;
			} else {
				return (String) this.get(this.size() - 1);
			}
		}
	}
	private final PhaseStack phaseStack = new PhaseStack();
	private final static String allWildcard = "ALL";


	public PhaseDumper() {
		if (! Options.getInstance().dump_cfg().isEmpty()) {
			cfgDumpingPhases = Options.getInstance().dump_cfg();
		}
	}


	private boolean isCFGDumpingPhase(String phaseName) {
		if (cfgDumpingPhases == null) {
			return false;
		}
		if (cfgDumpingPhases.contains(allWildcard)) {
			return true;
		} else {
			while (true) { // loop exited by "return" or "break".
				if (cfgDumpingPhases.contains(phaseName)) {
					return true;
				}
				// Go on to check if phaseName is a subphase of a
				// phase in cfgDumpingPhases.
				int lastDot = phaseName.lastIndexOf('.');
				if (lastDot < 0) {
					break;
				} else {
					phaseName = phaseName.substring(0, lastDot);
				}
			}
			return false;
		}
	}


	private static java.io.File makeDirectoryIfMissing(Body b)
			throws java.io.IOException {
		StringBuilder buf =
				new StringBuilder(soot.SourceLocator.v().getOutputDir());
		buf.append(File.separatorChar);
		String className = b.getMethod().getDeclaringClass().getName();
		buf.append(className);
		buf.append(File.separatorChar);
		buf.append(b.getMethod().getSubSignature().replace('<', '[').replace('>', ']'));
		java.io.File dir = new java.io.File(buf.toString());
		if (dir.exists()) {
			if (! dir.isDirectory()) {
				throw new java.io.IOException(dir.getPath() + " exists but is not a directory.");
			}
		} else {
			if (! dir.mkdirs())  {
				throw new java.io.IOException("Unable to mkdirs " + dir.getPath());
			}
		}
		return dir;
	}


	/**
	 * Returns the next available name for a graph file.
	 */

	private static String nextGraphFileName(Body b, String baseName)
			throws java.io.IOException {
		// We number output files to allow multiple graphs per phase.
		File dir = makeDirectoryIfMissing(b);
		final String prefix = dir.toString() + File.separatorChar + baseName;
		File file = null;
		int fileNumber = 0;
		do {
			file = new File(prefix + fileNumber + DotGraph.DOT_EXTENSION);
			fileNumber++;
		} while (file.exists());
		return file.toString();
	}


	// soot.Printer itself needs to create a BriefUnitGraph in order
	// to format the text for a method's instructions, so this flag is
	// a hack to avoid dumping graphs that we create in the course of
	// dumping bodies or other graphs.
	//
	// Note that this hack would not work if a PhaseDumper might be
	// accessed by multiple threads.  So long as there is a single
	// active PhaseDumper accessed through soot.G, it seems
	// safe to assume it will be accessed by only a single thread.
	private boolean alreadyDumping = false;


	/**
	 * Asks the <code>PhaseDumper</code> to dump the passed {@link
	 * DirectedGraph} if the current phase is being dumped.
	 *
	 * @param g the graph to dump.
	 *
	 */
	public void dumpGraph(DirectedGraph g, Body b) {
		if (alreadyDumping) {
			return;
		}
		try {
			alreadyDumping = true;
			String phaseName = phaseStack.currentPhase();
			if (isCFGDumpingPhase(phaseName)) {
				try {
					String outputFile = nextGraphFileName(b, phaseName + "-" +
							getClassIdent(g) + "-");
					DotGraph dotGraph = new CFGToDotGraph().drawCFG(g, b);
					dotGraph.plot(outputFile);

				} catch (java.io.IOException e) {
					// Don't abort execution because of an I/O error, but
					// report the error.
					System.out.println("PhaseDumper.dumpBody() caught: " +
							e.toString());
					e.printStackTrace(System.out);
				}
			}
		} finally {
			alreadyDumping = false;
		}
	}


	/**
	 * Asks the <code>PhaseDumper</code> to dump the passed {@link
	 * ExceptionalGraph} if the current phase is being dumped.
	 *
	 * @param g the graph to dump.
	 */
	public void dumpGraph(ExceptionalGraph g) {
		if (alreadyDumping) {
			return;
		}
		try {
			alreadyDumping = true;
			String phaseName = phaseStack.currentPhase();
			if (isCFGDumpingPhase(phaseName)) {
				try {
					String outputFile = nextGraphFileName(g.getBody(),
							phaseName + "-" +
									getClassIdent(g) + "-");
					CFGToDotGraph drawer = new CFGToDotGraph();
					drawer.setShowExceptions(Options.getInstance().show_exception_dests());
					DotGraph dotGraph = drawer.drawCFG(g);
					dotGraph.plot(outputFile);

				} catch (java.io.IOException e) {
					// Don't abort execution because of an I/O error, but
					// report the error.
					System.out.println("PhaseDumper.dumpBody() caught: " +
							e.toString());
					e.printStackTrace(System.out);
				}
			}
		} finally {
			alreadyDumping = false;
		}
	}

	/**
	 * A utility routine that returns the unqualified identifier
	 * naming the class of an object.
	 *
	 * @param obj The object whose class name is to be returned.
	 */
	private String getClassIdent(Object obj) {
		String qualifiedName = obj.getClass().getName();
		int lastDotIndex = qualifiedName.lastIndexOf('.');
		return qualifiedName.substring(lastDotIndex+1);
	}
}
