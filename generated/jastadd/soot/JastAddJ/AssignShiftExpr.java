/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production AssignShiftExpr : {@link AssignExpr};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:119
 */
public abstract class AssignShiftExpr extends AssignExpr implements Cloneable {
    /**
     * @ast method
     */
    public AssignShiftExpr() {
        super();


    }

    /**
     * @ast method
     */
    public AssignShiftExpr(Expr p0, Expr p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    /**
     * @apilevel low-level
     */
    public void flushCache() {
        super.flushCache();
    }

    /**
     * @apilevel internal
     */
    public void flushCollectionCache() {
        super.flushCollectionCache();
    }

    /**
     * @apilevel internal
     */
    @SuppressWarnings({"unchecked", "cast"})
    public AssignShiftExpr clone() throws CloneNotSupportedException {
        AssignShiftExpr node = (AssignShiftExpr) super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }

    /**
     * @ast method
     * @aspect TypeCheck
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeCheck.jrag:92
     */
    public void typeCheck() {
        if (!sourceType().isIntegralType() || !getDest().type().isIntegralType())
            error("Shift operators only operate on integral types");
        super.typeCheck();
    }

    /**
     * Initializes the child array to the correct size.
     * Initializes List and Opt nta children.
     *
     * @apilevel internal
     * @ast method
     * @ast method
     */
    public void init$Children() {
        children = new ASTNode[2];
    }

    /**
     * @apilevel low-level
     * @ast method
     */
    protected int numChildren() {
        return 2;
    }

    /**
     * @apilevel internal
     * @ast method
     */
    public boolean mayHaveRewrite() {
        return false;
    }

    /**
     * Retrieves the Dest child.
     *
     * @return The current node used as the Dest child.
     * @apilevel high-level
     * @ast method
     */
    public Expr getDest() {
        return (Expr) getChild(0);
    }

    /**
     * Replaces the Dest child.
     *
     * @param node The new node to replace the Dest child.
     * @apilevel high-level
     * @ast method
     */
    public void setDest(Expr node) {
        setChild(node, 0);
    }

    /**
     * Retrieves the Dest child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the Dest child.
     * @apilevel low-level
     * @ast method
     */
    public Expr getDestNoTransform() {
        return (Expr) getChildNoTransform(0);
    }

    /**
     * Retrieves the Source child.
     *
     * @return The current node used as the Source child.
     * @apilevel high-level
     * @ast method
     */
    public Expr getSource() {
        return (Expr) getChild(1);
    }

    /**
     * Replaces the Source child.
     *
     * @param node The new node to replace the Source child.
     * @apilevel high-level
     * @ast method
     */
    public void setSource(Expr node) {
        setChild(node, 1);
    }

    /**
     * Retrieves the Source child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the Source child.
     * @apilevel low-level
     * @ast method
     */
    public Expr getSourceNoTransform() {
        return (Expr) getChildNoTransform(1);
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        return super.rewriteTo();
    }
}
