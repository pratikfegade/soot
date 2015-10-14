package soot.jimple.spark.sets;

import soot.G;
import soot.Singletons;
import soot.jimple.spark.sets.SharedListSet.ListNode;
import soot.jimple.spark.sets.SharedListSet.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton to hold the hash table for SharedListSet
 */

public class AllSharedListNodes {
    public Map<Pair, ListNode> allNodes = new HashMap<Pair, ListNode>();

    public AllSharedListNodes(Singletons.Global g) {
    }

    public static AllSharedListNodes v() {
        return G.v().soot_jimple_spark_sets_AllSharedListNodes();
    }
}
