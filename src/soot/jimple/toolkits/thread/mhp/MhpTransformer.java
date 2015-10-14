package soot.jimple.toolkits.thread.mhp;

import soot.G;
import soot.SceneTransformer;
import soot.Singletons;

import java.util.Map;

/**
 *
 */

public class MhpTransformer extends SceneTransformer {
    MhpTester mhpTester;

    public MhpTransformer(Singletons.Global g) {
    }

    public static MhpTransformer v() {
        return G.v().soot_jimple_toolkits_thread_mhp_MhpTransformer();
    }

    protected void internalTransform(String phaseName, Map options) {
        getMhpTester().printMhpSummary();
    }

    public MhpTester getMhpTester() {
        if (mhpTester == null)
            mhpTester = new SynchObliviousMhpAnalysis();
        return mhpTester;
    }

    public void setMhpTester(MhpTester mhpTester) {
        this.mhpTester = mhpTester;
    }
}

