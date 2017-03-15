
package jastadd.soot.JastAddJ;

public interface FinallyHost {
    // Declared in DefiniteAssignment.jrag at line 908

    //public Block getFinally();
    boolean isDUafterFinally(Variable v);

    // Declared in DefiniteAssignment.jrag at line 909

    boolean isDAafterFinally(Variable v);

    // Declared in Statements.jrag at line 320


  void emitFinallyCode(Body b);


    // Declared in Statements.jrag at line 318
 @SuppressWarnings({"unchecked", "cast"})
 soot.jimple.Stmt label_finally_block();
}
