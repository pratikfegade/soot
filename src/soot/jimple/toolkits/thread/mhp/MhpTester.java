package soot.jimple.toolkits.thread.mhp;

import soot.SootMethod;
import soot.Unit;
import soot.jimple.toolkits.thread.AbstractRuntimeThread;

import java.util.List;

/**
 * MhpTester written by Richard L. Halpert 2007-03-15
 * An interface for any object that can provide May-Happen-in-Parallel info and
 * a list of the program's threads (List of AbstractRuntimeThreads)
 */

public interface MhpTester {
    boolean mayHappenInParallel(SootMethod m1, SootMethod m2); // method level MHP

    boolean mayHappenInParallel(SootMethod m1, Unit u1, SootMethod m2, Unit u2); // stmt level MHP

    void printMhpSummary();

    List<AbstractRuntimeThread> getThreads();
}
