/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production ArithmeticExpr : {@link Binary};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:152
 */
public abstract class ArithmeticExpr extends Binary implements Cloneable {
    /**
     * @ast method
     */
    public ArithmeticExpr() {
        super();


    }

    /**
     * @ast method
     */
    public ArithmeticExpr(Expr p0, Expr p1) {
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
    public ArithmeticExpr clone() throws CloneNotSupportedException {
        ArithmeticExpr node = (ArithmeticExpr) super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
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
     * Retrieves the LeftOperand child.
     *
     * @return The current node used as the LeftOperand child.
     * @apilevel high-level
     * @ast method
     */
    public Expr getLeftOperand() {
        return (Expr) getChild(0);
    }

    /**
     * Replaces the LeftOperand child.
     *
     * @param node The new node to replace the LeftOperand child.
     * @apilevel high-level
     * @ast method
     */
    public void setLeftOperand(Expr node) {
        setChild(node, 0);
    }

    /**
     * Retrieves the LeftOperand child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the LeftOperand child.
     * @apilevel low-level
     * @ast method
     */
    public Expr getLeftOperandNoTransform() {
        return (Expr) getChildNoTransform(0);
    }

    /**
     * Retrieves the RightOperand child.
     *
     * @return The current node used as the RightOperand child.
     * @apilevel high-level
     * @ast method
     */
    public Expr getRightOperand() {
        return (Expr) getChild(1);
    }

    /**
     * Replaces the RightOperand child.
     *
     * @param node The new node to replace the RightOperand child.
     * @apilevel high-level
     * @ast method
     */
    public void setRightOperand(Expr node) {
        setChild(node, 1);
    }

    /**
     * Retrieves the RightOperand child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the RightOperand child.
     * @apilevel low-level
     * @ast method
     */
    public Expr getRightOperandNoTransform() {
        return (Expr) getChildNoTransform(1);
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        return super.rewriteTo();
    }
}
