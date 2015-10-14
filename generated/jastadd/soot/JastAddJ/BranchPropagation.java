package soot.JastAddJ;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @ast interface
 */
public interface BranchPropagation {
    void collectBranches(Collection c);

    Stmt branchTarget(Stmt branchStmt);

    void collectFinally(Stmt branchStmt, ArrayList list);

    /**
     * @attribute syn
     * @aspect BranchTarget
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BranchTarget.jrag:32
     */
    @SuppressWarnings({"unchecked", "cast"}) Collection targetContinues();

    /**
     * @attribute syn
     * @aspect BranchTarget
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BranchTarget.jrag:33
     */
    @SuppressWarnings({"unchecked", "cast"}) Collection targetBreaks();

    /**
     * @attribute syn
     * @aspect BranchTarget
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BranchTarget.jrag:34
     */
    @SuppressWarnings({"unchecked", "cast"}) Collection targetBranches();

    /**
     * @attribute syn
     * @aspect BranchTarget
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BranchTarget.jrag:35
     */
    @SuppressWarnings({"unchecked", "cast"}) Collection escapedBranches();

    /**
     * @attribute syn
     * @aspect BranchTarget
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BranchTarget.jrag:36
     */
    @SuppressWarnings({"unchecked", "cast"}) Collection branches();

    /**
     * @attribute syn
     * @aspect BranchTarget
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BranchTarget.jrag:39
     */
    @SuppressWarnings({"unchecked", "cast"}) boolean targetOf(ContinueStmt stmt);

    /**
     * @attribute syn
     * @aspect BranchTarget
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BranchTarget.jrag:40
     */
    @SuppressWarnings({"unchecked", "cast"}) boolean targetOf(BreakStmt stmt);
}
