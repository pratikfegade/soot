/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production AndBitwiseExpr : {@link BitwiseExpr};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:167
 */
public class AndBitwiseExpr extends BitwiseExpr implements Cloneable {
    /**
     * @ast method
     */
    public AndBitwiseExpr() {
        super();


    }

    /**
     * @ast method
     */
    public AndBitwiseExpr(Expr p0, Expr p1) {
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
    public AndBitwiseExpr clone() throws CloneNotSupportedException {
        AndBitwiseExpr node = (AndBitwiseExpr) super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }

    /**
     * @apilevel internal
     */
    @SuppressWarnings({"unchecked", "cast"})
    public AndBitwiseExpr copy() {
        try {
            AndBitwiseExpr node = clone();
            node.parent = null;
            if (children != null)
                node.children = children.clone();
            return node;
        } catch (CloneNotSupportedException e) {
            throw new Error("Error: clone not supported for " +
                    getClass().getName());
        }
    }

    /**
     * Create a deep copy of the AST subtree at this node.
     * The copy is dangling, i.e. has no parent.
     *
     * @return dangling copy of the subtree at this node
     * @apilevel low-level
     */
    @SuppressWarnings({"unchecked", "cast"})
    public AndBitwiseExpr fullCopy() {
        AndBitwiseExpr tree = copy();
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                ASTNode child = children[i];
                if (child != null) {
                    child = child.fullCopy();
                    tree.setChild(child, i);
                }
            }
        }
        return tree;
    }

    /**
     * @ast method
     * @aspect Expressions
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/Expressions.jrag:832
     */
    public soot.Value emitOperation(Body b, soot.Value left, soot.Value right) {
        return asLocal(b, b.newAndExpr(asImmediate(b, left), asImmediate(b, right), this));
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
     * @attribute syn
     * @aspect ConstantExpression
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:91
     */
    public Constant constant() {
        ASTNode$State state = state();
        try {
            return type().andBitwise(getLeftOperand().constant(), getRightOperand().constant());
        } finally {
        }
    }

    /**
     * @attribute syn
     * @aspect PrettyPrint
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:400
     */
    public String printOp() {
        ASTNode$State state = state();
        try {
            return " & ";
        } finally {
        }
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        return super.rewriteTo();
    }
}
