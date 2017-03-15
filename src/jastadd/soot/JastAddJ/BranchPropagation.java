
package jastadd.soot.JastAddJ;

import java.util.ArrayList;
import java.util.Collection;
// ---------------------------------------------------------------------------

  // propagate branch statements to the statements that are their respective
  // targets taking finally blocks that can not complete normally into account
public interface BranchPropagation {
    // Declared in BranchTarget.jrag at line 58

  void collectBranches(Collection c);


    // Declared in BranchTarget.jrag at line 157

  Stmt branchTarget(Stmt branchStmt);


    // Declared in BranchTarget.jrag at line 195

  void collectFinally(Stmt branchStmt, ArrayList list);


    // Declared in BranchTarget.jrag at line 33
 @SuppressWarnings({"unchecked", "cast"})
 Collection targetContinues();
    // Declared in BranchTarget.jrag at line 34
 @SuppressWarnings({"unchecked", "cast"})
 Collection targetBreaks();
    // Declared in BranchTarget.jrag at line 35
 @SuppressWarnings({"unchecked", "cast"})
 Collection targetBranches();
    // Declared in BranchTarget.jrag at line 36
 @SuppressWarnings({"unchecked", "cast"})
 Collection escapedBranches();
    // Declared in BranchTarget.jrag at line 37
 @SuppressWarnings({"unchecked", "cast"})
 Collection branches();
    // Declared in BranchTarget.jrag at line 40
 @SuppressWarnings({"unchecked", "cast"})
 boolean targetOf(ContinueStmt stmt);
    // Declared in BranchTarget.jrag at line 41
 @SuppressWarnings({"unchecked", "cast"})
 boolean targetOf(BreakStmt stmt);
}
