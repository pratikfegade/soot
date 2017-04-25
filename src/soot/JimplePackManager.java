/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003, 2004 Ondrej Lhotak
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

package soot;

import heros.solver.CountingThreadPoolExecutor;
import soot.jimple.toolkits.base.Aggregator;
import soot.jimple.toolkits.scalar.LocalNameStandardizer;
import soot.jimple.toolkits.typing.TypeAssigner;
import soot.options.Options;
import soot.toolkits.scalar.ConstantValueToInitializerTransformer;
import soot.util.EscapedWriter;

import java.io.*;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/** Manages the Packs containing the various phases and their options. */
public class JimplePackManager {
    public static boolean DEBUG=false;
    private Pack _pack;

    public JimplePackManager()
    {
        // Jimple body creation
        _pack = new JimpleBodyPack(this);
        _pack.add(new Transform("jb.lns", new LocalNameStandardizer()));
        _pack.add(new Transform("jb.a", new Aggregator()));
        _pack.add(new Transform("jb.tr", new TypeAssigner()));
        _pack.add(new Transform("jb.lns", new LocalNameStandardizer()));
    }

    public void runPacks(Body b) {
        // Apply the three Jimple body transformations
        for (Transform t : _pack.opts)
            t.apply(b);
    }

    private Iterator<SootClass> classes() {
        return Scene.getInstance().getClasses().snapshotIterator();
    }

    public static void writeClass(SootClass c) {
        // Create code assignments for those values we only have in code assignments
        if (!c.isPhantom)
           ConstantValueToInitializerTransformer.v().transformClass(c);

        final int format = Options.getInstance().output_format();

        OutputStream streamOut;
        PrintWriter writerOut;

        String fileName = SourceLocator.v().getFileNameFor(c, format);
        try {

            new File(fileName).getParentFile().mkdirs();
            streamOut = new FileOutputStream(fileName);
        } catch (IOException e) {
            throw new CompilationDeathException("Cannot output file " + fileName,e);
        }

        switch (format) {
            case Options.output_format_jimple :
            case Options.output_format_shimple :
                writerOut =
                        new PrintWriter(new EscapedWriter(new OutputStreamWriter(streamOut)));
                Printer.printTo(c, writerOut);
                break;
            default :
                throw new RuntimeException();
        }

       writerOut.flush();
    }

    public void retrieveAllSceneClassesBodies() {
        int threadNum = Runtime.getRuntime().availableProcessors();
        CountingThreadPoolExecutor executor =  new CountingThreadPoolExecutor(threadNum,
                threadNum, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        Iterator<SootClass> clIt = classes();
        while( clIt.hasNext() ) {
            SootClass cl = clIt.next();
            //note: the following is a snapshot iterator;
            //this is necessary because it can happen that phantom methods
            //are added during resolution
            for (SootMethod m : cl.getMethods()) {
                if (m.isConcrete()) {
                    executor.execute(m::retrieveActiveBody);
                }
            }
        }

        // Wait till all method bodies have been loaded
        try {
            executor.awaitCompletion();
            executor.shutdown();
        } catch (InterruptedException e) {
            // Something went horribly wrong
            throw new RuntimeException("Could not wait for loader threads to " + "finish: " + e.getMessage(), e);
        }

        // If something went wrong, we tell the world
        if (executor.getException() != null)
            throw (RuntimeException) executor.getException();
    }
}
