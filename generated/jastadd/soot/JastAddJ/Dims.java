/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production Dims : {@link ASTNode} ::= <span class="component">[{@link Expr}]</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:134
 */
public class Dims extends ASTNode<ASTNode> implements Cloneable {
    /**
     * @ast method
     */
    public Dims() {
        super();


    }

    /**
     * @ast method
     */
    public Dims(Opt<Expr> p0) {
        setChild(p0, 0);
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
    public Dims clone() throws CloneNotSupportedException {
        Dims node = (Dims) super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }

    /**
     * @apilevel internal
     */
    @SuppressWarnings({"unchecked", "cast"})
    public Dims copy() {
        try {
            Dims node = clone();
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
    public Dims fullCopy() {
        Dims tree = copy();
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
     * Initializes the child array to the correct size.
     * Initializes List and Opt nta children.
     *
     * @apilevel internal
     * @ast method
     * @ast method
     */
    public void init$Children() {
        children = new ASTNode[1];
        setChild(new Opt(), 0);
    }

    /**
     * @apilevel low-level
     * @ast method
     */
    protected int numChildren() {
        return 1;
    }

    /**
     * @apilevel internal
     * @ast method
     */
    public boolean mayHaveRewrite() {
        return false;
    }

    /**
     * Check whether the optional Expr child exists.
     *
     * @return {@code true} if the optional Expr child exists, {@code false} if it does not.
     * @apilevel high-level
     * @ast method
     */
    public boolean hasExpr() {
        return getExprOpt().getNumChild() != 0;
    }

    /**
     * Retrieves the (optional) Expr child.
     *
     * @return The Expr child, if it exists. Returns {@code null} otherwise.
     * @apilevel low-level
     * @ast method
     */
    @SuppressWarnings({"unchecked", "cast"})
    public Expr getExpr() {
        return getExprOpt().getChild(0);
    }

    /**
     * Replaces the (optional) Expr child.
     *
     * @param node The new node to be used as the Expr child.
     * @apilevel high-level
     * @ast method
     */
    public void setExpr(Expr node) {
        getExprOpt().setChild(node, 0);
    }

    /**
     * @apilevel low-level
     * @ast method
     */
    @SuppressWarnings({"unchecked", "cast"})
    public Opt<Expr> getExprOpt() {
        return (Opt<Expr>) getChild(0);
    }

    /**
     * Replaces the optional node for the Expr child. This is the {@code Opt} node containing the child Expr, not the actual child!
     *
     * @param opt The new node to be used as the optional node for the Expr child.
     * @apilevel low-level
     * @ast method
     */
    public void setExprOpt(Opt<Expr> opt) {
        setChild(opt, 0);
    }

    /**
     * Retrieves the optional node for child Expr. This is the {@code Opt} node containing the child Expr, not the actual child!
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The optional node for child Expr.
     * @apilevel low-level
     * @ast method
     */
    @SuppressWarnings({"unchecked", "cast"})
    public Opt<Expr> getExprOptNoTransform() {
        return (Opt<Expr>) getChildNoTransform(0);
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        return super.rewriteTo();
    }
}
