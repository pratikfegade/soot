/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production IdUse : {@link ASTNode} ::= <span class="component">&lt;ID:String&gt;</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:102
 */
public class IdUse extends ASTNode<ASTNode> implements Cloneable {
    /**
     * @ast method
     */

    public int IDstart;
    /**
     * @ast method
     */

    public int IDend;
    /**
     * @apilevel internal
     */
    protected String tokenString_ID;

    /**
     * @ast method
     */
    public IdUse() {
        super();

        is$Final(true);

    }

    /**
     * @ast method
     */
    public IdUse(String p0) {
        setID(p0);
        is$Final(true);
    }

    /**
     * @ast method
     */
    public IdUse(beaver.Symbol p0) {
        setID(p0);
        is$Final(true);
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
    public IdUse clone() throws CloneNotSupportedException {
        IdUse node = (IdUse) super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }

    /**
     * @apilevel internal
     */
    @SuppressWarnings({"unchecked", "cast"})
    public IdUse copy() {
        try {
            IdUse node = clone();
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
    public IdUse fullCopy() {
        IdUse tree = copy();
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
     *
     */

    /**
     * @apilevel low-level
     * @ast method
     */
    protected int numChildren() {
        return 0;
    }

    /**
     * @apilevel internal
     * @ast method
     */
    public boolean mayHaveRewrite() {
        return false;
    }

    /**
     * Replaces the lexeme ID.
     *
     * @param value The new value for the lexeme ID.
     * @apilevel high-level
     * @ast method
     */
    public void setID(String value) {
        tokenString_ID = value;
    }

    /**
     * Retrieves the value for the lexeme ID.
     *
     * @return The value for the lexeme ID.
     * @apilevel high-level
     * @ast method
     */
    public String getID() {
        return tokenString_ID != null ? tokenString_ID : "";
    }

    /**
     * JastAdd-internal setter for lexeme ID using the Beaver parser.
     *
     * @apilevel internal
     * @ast method
     */
    public void setID(beaver.Symbol symbol) {
        if (symbol.value != null && !(symbol.value instanceof String))
            throw new UnsupportedOperationException("setID is only valid for String lexemes");
        tokenString_ID = (String) symbol.value;
        IDstart = symbol.getStart();
        IDend = symbol.getEnd();
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        return super.rewriteTo();
    }
}
