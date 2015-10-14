package soot.jimple.toolkits.ide.icfg;

import heros.InterproceduralCFG;
import soot.Value;
import soot.toolkits.graph.DirectedGraph;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * An {@link InterproceduralCFG} which supports the computation of predecessors.
 */
public interface BiDiInterproceduralCFG<N, M> extends InterproceduralCFG<N, M> {

    List<N> getPredsOf(N u);

    Collection<N> getEndPointsOf(M m);

    List<N> getPredsOfCallAt(N u);

    Set<N> allNonCallEndNodes();

    //also exposed to some clients who need it
    DirectedGraph<N> getOrCreateUnitGraph(M body);

    List<Value> getParameterRefs(M m);

    /**
     * Gets whether the given statement is a return site of at least one call
     *
     * @param n The statement to check
     * @return True if the given statement is a return site, otherwise false
     */
    boolean isReturnSite(N n);

}
