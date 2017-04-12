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
import soot.jimple.toolkits.annotation.LineNumberAdder;
import soot.jimple.toolkits.base.Aggregator;
import soot.jimple.toolkits.scalar.*;
import soot.jimple.toolkits.typing.TypeAssigner;
import soot.options.Options;
import soot.shimple.Shimple;
import soot.shimple.ShimpleBody;
import soot.singletons.Singletons;
import soot.tagkit.InnerClassTagAggregator;
import soot.toDex.DexPrinter;
import soot.toolkits.exceptions.DuplicateCatchAllTrapRemover;
import soot.toolkits.exceptions.TrapTightener;
import soot.toolkits.graph.interaction.InteractionHandler;
import soot.toolkits.scalar.*;
import soot.util.EscapedWriter;
import soot.util.PhaseDumper;
import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
// [AM]
//import soot.javaToJimple.toolkits.*;

/** Manages the Packs containing the various phases and their options. */
public class PackManager {
    public static boolean DEBUG=false;
    public PackManager( Singletons.Global g ) { PhaseOptions.v().setPackManager(this); init(); }
    public boolean onlyStandardPacks() { return onlyStandardPacks; }
    private boolean onlyStandardPacks = false;
    void notifyAddPack() {
        onlyStandardPacks = false;
    }

    private void init()
    {
        Pack p;

        // Jimple body creation
        addPack(p = new JimpleBodyPack());
        {
            p.add(new Transform("jb.tt", TrapTightener.v()));
            p.add(new Transform("jb.dtr", DuplicateCatchAllTrapRemover.v()));
            p.add(new Transform("jb.ese", EmptySwitchEliminator.v()));
            p.add(new Transform("jb.ls", LocalSplitter.v()));
            p.add(new Transform("jb.a", Aggregator.v()));
            p.add(new Transform("jb.ule", UnusedLocalEliminator.v()));
            p.add(new Transform("jb.tr", TypeAssigner.v()));
            p.add(new Transform("jb.ulp", LocalPacker.v()));
            p.add(new Transform("jb.lns", LocalNameStandardizer.v()));
            p.add(new Transform("jb.cp", CopyPropagator.v()));
            p.add(new Transform("jb.cp-ule", UnusedLocalEliminator.v()));
            p.add(new Transform("jb.lp", LocalPacker.v()));
            p.add(new Transform("jb.ne", NopEliminator.v()));
            p.add(new Transform("jb.uce", UnreachableCodeEliminator.v()));
        }

        // Shimple pack
        addPack(p = new BodyPack(Shimple.PHASE));

        // Shimple transformation pack
        addPack(p = new BodyPack("stp"));


        // Jimple transformation pack
        addPack(p = new BodyPack("jtp"));

        onlyStandardPacks = true;
    }

    public static PackManager v() {
        return G.v().soot_PackManager();
    }

    private final Map<String, Pack> packNameToPack = new HashMap<>();
    private final List<Pack> packList = new LinkedList<>();

    private void addPack( Pack p ) {
        if( packNameToPack.containsKey( p.getPhaseName() ) )
            throw new RuntimeException( "Duplicate pack "+p.getPhaseName() );
        packNameToPack.put( p.getPhaseName(), p );
        packList.add( p );
    }

    public boolean hasPack(String phaseName) {
        return getPhase( phaseName ) != null;
    }

    public Pack getPack(String phaseName) {
        Pack p = packNameToPack.get(phaseName);
        return p;
    }

    public boolean hasPhase(String phaseName) {
        return getPhase(phaseName) != null;
    }

    public HasPhaseOptions getPhase(String phaseName) {
        int index = phaseName.indexOf( "." );
        if( index < 0 ) return getPack( phaseName );
        String packName = phaseName.substring(0,index);
        if( !hasPack( packName ) ) return null;
        return getPack( packName ).get( phaseName );
    }

    public Transform getTransform(String phaseName) {
        return (Transform) getPhase( phaseName );
    }


    public Collection<Pack> allPacks() {
        return Collections.unmodifiableList( packList );
    }

    public void runPacks() {
        runPacksNormally();

    }

    private void runPacksNormally() {

        if (Options.v().src_prec() == Options.src_prec_class && Options.v().keep_line_number()){
            LineNumberAdder lineNumAdder = LineNumberAdder.v();
            lineNumAdder.internalTransform("", null);
        }

        retrieveAllBodies();

        // Create tags from all values we only have in code assignments now
        for (SootClass sc : Scene.v().getApplicationClasses()) {
            if( Options.v().validate() )
                sc.validate();
            if (!sc.isPhantom)
                ConstantInitializerToTagTransformer.v().transformClass(sc, true);
        }

        if (Options.v().interactive_mode()){
            if (InteractionHandler.v().getInteractionListener() == null){
                G.v().out.println("Cannot run in interactive mode. No listeners available. Continuing in regular mode.");
                Options.v().set_interactive_mode(false);
            }
            else {
                G.v().out.println("Running in interactive mode.");
            }
        }
        runBodyPacks();
        handleInnerClasses();
    }


    public void runBodyPacks() {
        runBodyPacks( reachableClasses() );
    }

    private JarOutputStream jarFile = null;

    public JarOutputStream getJarFile() {
        return jarFile;
    }

    public void writeOutput() {
        setupJAR();
        if(Options.v().verbose())
            PhaseDumper.v().dumpBefore("output");
        if (Options.v().output_format() == Options.output_format_dex
                || Options.v().output_format() == Options.output_format_force_dex) {
            dexPrinter = new DexPrinter();
            writeOutput(reachableClasses());
            dexPrinter.print();
            dexPrinter = null;
        } else {
            writeOutput( reachableClasses() );
            tearDownJAR();
        }
        if (!Options.v().no_writeout_body_releasing())
            releaseBodies( reachableClasses() );
        if(Options.v().verbose())
            PhaseDumper.v().dumpAfter("output");
    }

    private DexPrinter dexPrinter = null;

    private void setupJAR() {
        if (Options.v().output_jar()) {
            String outFileName = SourceLocator.v().getOutputJarName();
            try {
                jarFile = new JarOutputStream(new FileOutputStream(outFileName));
            } catch (IOException e) {
                throw new CompilationDeathException("Cannot open output Jar file " + outFileName);
            }
        } else {
            jarFile = null;
        }
    }

    private void runBodyPacks( final Iterator<SootClass> classes ) {
        int threadNum = Runtime.getRuntime().availableProcessors();
        CountingThreadPoolExecutor executor =  new CountingThreadPoolExecutor(threadNum,
                threadNum, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        while( classes.hasNext() ) {
            final SootClass c = classes.next();
            executor.execute(() -> runBodyPacks(c));
        }

        // Wait till all packs have been executed
        try {
            executor.awaitCompletion();
            executor.shutdown();
        } catch (InterruptedException e) {
            // Something went horribly wrong
            throw new RuntimeException("Could not wait for pack threads to "
                    + "finish: " + e.getMessage(), e);
        }

        // If something went wrong, we tell the world
        if (executor.getException() != null)
            throw (RuntimeException) executor.getException();
    }

    private void handleInnerClasses(){
        InnerClassTagAggregator agg = InnerClassTagAggregator.v();
        agg.internalTransform("", null);
    }

    private void writeOutput( Iterator<SootClass> classes ) {
        // If we're writing individual class files, we can write them
        // concurrently. Otherwise, we need to synchronize for not destroying
        // the shared output stream.
        int threadNum = Options.v().output_format() == Options.output_format_class
                && jarFile == null ? Runtime.getRuntime().availableProcessors() : 1;
        CountingThreadPoolExecutor executor =  new CountingThreadPoolExecutor(threadNum,
                threadNum, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        while( classes.hasNext() ) {
            final SootClass c = classes.next();
            executor.execute(() -> writeClass(c));
        }

        // Wait till all classes have been written
        try {
            executor.awaitCompletion();
            executor.shutdown();
        } catch (InterruptedException e) {
            // Something went horribly wrong
            throw new RuntimeException("Could not wait for writer threads to "
                    + "finish: " + e.getMessage(), e);
        }

        // If something went wrong, we tell the world
        if (executor.getException() != null) {
            if (executor.getException() instanceof RuntimeException)
                throw (RuntimeException) executor.getException();
            else
                throw new RuntimeException(executor.getException());
        }
    }

    private void tearDownJAR() {
        try {
            if(jarFile != null) jarFile.close();
        } catch( IOException e ) {
            throw new CompilationDeathException( "Error closing output jar: "+e );
        }
    }

    private void releaseBodies( Iterator<SootClass> classes ) {
        while( classes.hasNext() ) {
            releaseBodies( classes.next() );
        }
    }

    private Iterator<SootClass> reachableClasses() {
        return Scene.v().getApplicationClasses().snapshotIterator();
    }

    private Iterator<SootClass> classes() {
        return Scene.v().getClasses().snapshotIterator();
    }

    @SuppressWarnings("fallthrough")
    private void runBodyPacks(SootClass c) {

        final int format = Options.v().output_format();
        if (format == Options.output_format_dava) {
            G.v().out.print("Decompiling ");

            //January 13th, 2006  SootMethodAddedByDava is set to false for SuperFirstStmtHandler
            G.v().SootMethodAddedByDava=false;
        } else {
            G.v().out.print("Transforming ");
        }
        G.v().out.println(c.getName() + "... ");

        boolean produceJimple = true, produceShimple = false;

        switch (format) {
            case Options.output_format_none :
            case Options.output_format_xml :
            case Options.output_format_jimple :
            case Options.output_format_jimp :
            case Options.output_format_template :
            case Options.output_format_dex :
            case Options.output_format_force_dex :
                break;
            case Options.output_format_shimp:
            case Options.output_format_shimple:
                produceShimple = true;
                // FLIP produceJimple
                produceJimple = false;
                break;
            default :
                throw new RuntimeException();
        }

        boolean wholeShimple = Options.v().whole_shimple();
        if( Options.v().via_shimple() ) produceShimple = true;

        //here we create a copy of the methods so that transformers are able
        //to add method bodies during the following iteration;
        //such adding of methods happens in rare occasions: for instance when
        //resolving a method reference to a non-existing method, then this
        //method is created as a phantom method when phantom-refs are enabled
        LinkedList<SootMethod> methodsCopy = new LinkedList<SootMethod>(c.getMethods());
        for (SootMethod m : methodsCopy) {
            if(DEBUG){
                if(m.getExceptions().size()!=0)
                    System.out.println("PackManager printing out jimple body exceptions for method "+m.toString()+" " + m.getExceptions().toString());
            }

            if (!m.isConcrete()) continue;
            if (produceShimple || wholeShimple) {
                ShimpleBody sBody;
                System.out.println("Producing shimple bodies");
                // whole shimple or not?
                {
                    Body body = m.retrieveActiveBody();

                    if(body instanceof ShimpleBody){
                        sBody = (ShimpleBody) body;
                        if(!sBody.isSSA())
                            sBody.rebuild();
                    }
                    else{
                        sBody = Shimple.v().newBody(body);
                    }
                }

                m.setActiveBody(sBody);
                PackManager.v().getPack("stp").apply(sBody);
                PackManager.v().getPack("sop").apply(sBody);
//                PackManager.v().getTransform("jb.dr").apply(sBody);            // DoopRenamer

                if( produceJimple || (wholeShimple && !produceShimple) )
                    m.setActiveBody(sBody.toJimpleBody());
            }

            if (produceJimple) {
                Body body = m.retrieveActiveBody();
                //Change
                CopyPropagator.v().transform(body);
                ConditionalBranchFolder.v().transform(body);
                UnreachableCodeEliminator.v().transform(body);
                UnusedLocalEliminator.v().transform(body);
                PackManager.v().getPack("jtp").apply(body);
                if( Options.v().validate() ) {
                    body.validate();
                }
                PackManager.v().getPack("jop").apply(body);
                PackManager.v().getPack("jap").apply(body);
            }
        }

    }

    public void writeClass(SootClass c) {
        // Create code assignments for those values we only have in code assignments
        if (Options.v().output_format() == Options.output_format_jimple)
            if (!c.isPhantom)
                ConstantValueToInitializerTransformer.v().transformClass(c);

        final int format = Options.v().output_format();
        if( format == Options.output_format_none ) return;
        if( format == Options.output_format_dava ) return;
        if (format == Options.output_format_dex
                || format == Options.output_format_force_dex) {
            // just add the class to the dex printer, writing is done after adding all classes
            dexPrinter.add(c);
            return;
        }

        OutputStream streamOut;
        PrintWriter writerOut;

        String fileName = SourceLocator.v().getFileNameFor(c, format);
        if( Options.v().gzip() ) fileName = fileName+".gz";

        try {
            if( jarFile != null ) {
                // Fix path delimiters according to ZIP specification
                fileName = fileName.replace("\\", "/");
                JarEntry entry = new JarEntry(fileName);
                entry.setMethod(ZipEntry.DEFLATED);
                jarFile.putNextEntry(entry);
                streamOut = jarFile;
            } else {
                new File(fileName).getParentFile().mkdirs();
                streamOut = new FileOutputStream(fileName);
            }
            if( Options.v().gzip() ) {
                streamOut = new GZIPOutputStream(streamOut);
            }
            writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
            //G.v().out.println( "Writing to "+fileName );
        } catch (IOException e) {
            throw new CompilationDeathException("Cannot output file " + fileName,e);
        }

        if (Options.v().xml_attributes()) {
            Printer.v().setOption(Printer.ADD_JIMPLE_LN);
        }

        switch (format) {
            case Options.output_format_jimp :
            case Options.output_format_shimp :
                Printer.v().setOption(Printer.USE_ABBREVIATIONS);
                Printer.v().printTo(c, writerOut);
                break;
            case Options.output_format_jimple :
            case Options.output_format_shimple :
                writerOut =
                        new PrintWriter(
                                new EscapedWriter(new OutputStreamWriter(streamOut)));
                Printer.v().printTo(c, writerOut);
                break;
            default :
                throw new RuntimeException();
        }

        try {
            writerOut.flush();
            if( jarFile == null ) {
                streamOut.close();
                writerOut.close();
            }
            else
                jarFile.closeEntry();
        } catch (IOException e) {
            throw new CompilationDeathException("Cannot close output file " + fileName);
        }
    }

    private void releaseBodies( SootClass cl ) {
        Iterator<SootMethod> methodIt = cl.methodIterator();
        while (methodIt.hasNext()) {
            SootMethod m = methodIt.next();

            if (m.hasActiveBody())
                m.releaseActiveBody();
        }
    }

    public void retrieveAllBodies() {
        // The old coffi front-end is not thread-safe
        int threadNum = Options.v().coffi() ? 1 : Runtime.getRuntime().availableProcessors();

        CountingThreadPoolExecutor executor =  new CountingThreadPoolExecutor(threadNum,
                threadNum, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        Iterator<SootClass> clIt = reachableClasses();
        while( clIt.hasNext() ) {
            SootClass cl = clIt.next();
            //note: the following is a snapshot iterator;
            //this is necessary because it can happen that phantom methods
            //are added during resolution
            Iterator<SootMethod> methodIt = cl.getMethods().iterator();
            while (methodIt.hasNext()) {
                final SootMethod m = methodIt.next();
                if( m.isConcrete() ) {
                    executor.execute(new Runnable() {

                        @Override
                        public void run() {
                            m.retrieveActiveBody();
                        }

                    });
                }
            }
        }

        // Wait till all method bodies have been loaded
        try {
            executor.awaitCompletion();
            executor.shutdown();
        } catch (InterruptedException e) {
            // Something went horribly wrong
            throw new RuntimeException("Could not wait for loader threads to "
                    + "finish: " + e.getMessage(), e);
        }

        // If something went wrong, we tell the world
        if (executor.getException() != null)
            throw (RuntimeException) executor.getException();
    }

    public void retrieveAllSceneClassesBodies() {
        // The old coffi front-end is not thread-safe
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
            Iterator<SootMethod> methodIt = cl.getMethods().iterator();
            while (methodIt.hasNext()) {
                final SootMethod m = methodIt.next();
                if( m.isConcrete() ) {
                    executor.execute(new Runnable() {

                        @Override
                        public void run() {
                            m.retrieveActiveBody();
                        }

                    });
                }
            }
        }

        // Wait till all method bodies have been loaded
        try {
            executor.awaitCompletion();
            executor.shutdown();
        } catch (InterruptedException e) {
            // Something went horribly wrong
            throw new RuntimeException("Could not wait for loader threads to "
                    + "finish: " + e.getMessage(), e);
        }

        // If something went wrong, we tell the world
        if (executor.getException() != null)
            throw (RuntimeException) executor.getException();
    }
}
