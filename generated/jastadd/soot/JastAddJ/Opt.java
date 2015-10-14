/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production Opt : {@link ASTNode};
 * @ast node
 */
public class Opt<T extends ASTNode> extends ASTNode<T> implements Cloneable {
    /**
     * @ast method
     */
    public Opt() {
        super();


    }

    /**
     * @ast method
     */
    public Opt(T opt) {
        setChild(opt, 0);
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
    public Opt<T> clone() throws CloneNotSupportedException {
        Opt node = (Opt) super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }

    /**
     * @apilevel internal
     */
    @SuppressWarnings({"unchecked", "cast"})
    public Opt<T> copy() {
        try {
            Opt node = (Opt) clone();
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
    public Opt<T> fullCopy() {
        Opt tree = (Opt) copy();
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
    }

    /**
     * @apilevel internal
     * @ast method
     */
    public boolean mayHaveRewrite() {
        return false;
    }

    /**
     * @attribute syn
     * @aspect BooleanExpressions
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:21
     */
    public boolean definesLabel() {
        ASTNode$State state = state();
        try {
            return getParent().definesLabel();
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
