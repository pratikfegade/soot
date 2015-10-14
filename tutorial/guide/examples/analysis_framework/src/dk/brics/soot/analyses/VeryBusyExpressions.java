package dk.brics.soot.analyses;

import soot.Unit;
import soot.jimple.internal.AbstractBinopExpr;

import java.util.List;

/**
 * Provides an interface for querying the expressions that are very busy
 * before and after a unit in a method.
 *
 * @author �rni Einarsson
 */
public interface VeryBusyExpressions {
    /**
     * Returns the list of expressions that are very busy before the specified
     * Unit.
     *
     * @param s the Unit that defines this query.
     * @return a list of expressions that are busy before the specified unit in the method.
     */
    List<AbstractBinopExpr> getBusyExpressionsBefore(Unit s);

    /**
     * Returns the list of expressions that are very busy after the specified
     * Unit.
     *
     * @param s the Unit that defines this query.
     * @return a list of expressions that are very busy after the specified unit in the method.
     */
    List<AbstractBinopExpr> getBusyExpressionsAfter(Unit s);
}
