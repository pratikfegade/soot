package soot.JastAddJ;

/**
 * @ast interface
 */
public interface FinallyHost {

    //public Block getFinally();

    //public Block getFinally();
    boolean isDUafterFinally(Variable v);


    boolean isDAafterFinally(Variable v);


    void emitFinallyCode(Body b);

    /**
     * @attribute syn
     * @aspect Statements
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/Statements.jrag:319
     */
    @SuppressWarnings({"unchecked", "cast"})
    soot.jimple.Stmt label_finally_block();
}
