/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production WildcardExtends : {@link AbstractWildcard} ::= <span class="component">{@link Access}</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.ast:22
 */
public class WildcardExtends extends AbstractWildcard implements Cloneable {
    /**
     * @apilevel internal
     */
    protected boolean type_computed = false;
    /**
     * @apilevel internal
     */
    protected TypeDecl type_value;

    /**
     * @ast method
     */
    public WildcardExtends() {
        super();


    }

    /**
     * @ast method
     */
    public WildcardExtends(Access p0) {
        setChild(p0, 0);
    }

    /**
     * @apilevel low-level
     */
    public void flushCache() {
        super.flushCache();
        type_computed = false;
        type_value = null;
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
    public WildcardExtends clone() throws CloneNotSupportedException {
        WildcardExtends node = (WildcardExtends) super.clone();
        node.type_computed = false;
        node.type_value = null;
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }

    /**
     * @apilevel internal
     */
    @SuppressWarnings({"unchecked", "cast"})
    public WildcardExtends copy() {
        try {
            WildcardExtends node = clone();
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
    public WildcardExtends fullCopy() {
        WildcardExtends tree = copy();
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
     * @aspect GenericsPrettyPrint
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/GenericsPrettyPrint.jrag:164
     */
    public void toString(StringBuffer s) {
        s.append("? extends ");
        getAccess().toString(s);
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
     * Retrieves the Access child.
     *
     * @return The current node used as the Access child.
     * @apilevel high-level
     * @ast method
     */
    public Access getAccess() {
        return (Access) getChild(0);
    }

    /**
     * Replaces the Access child.
     *
     * @param node The new node to replace the Access child.
     * @apilevel high-level
     * @ast method
     */
    public void setAccess(Access node) {
        setChild(node, 0);
    }

    /**
     * Retrieves the Access child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the Access child.
     * @apilevel low-level
     * @ast method
     */
    public Access getAccessNoTransform() {
        return (Access) getChildNoTransform(0);
    }

    /**
     * @attribute syn
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1370
     */
    @SuppressWarnings({"unchecked", "cast"})
    public TypeDecl type() {
        if (type_computed) {
            return type_value;
        }
        ASTNode$State state = state();
        int num = state.boundariesCrossed;
        boolean isFinal = this.is$Final();
        type_value = type_compute();
        if (isFinal && num == state().boundariesCrossed) type_computed = true;
        return type_value;
    }

    /**
     * @apilevel internal
     */
    private TypeDecl type_compute() {
        return lookupWildcardExtends(getAccess().type());
    }

    /**
     * @attribute inh
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1373
     */
    @SuppressWarnings({"unchecked", "cast"})
    public TypeDecl lookupWildcardExtends(TypeDecl typeDecl) {
        ASTNode$State state = state();
        TypeDecl lookupWildcardExtends_TypeDecl_value = getParent().Define_TypeDecl_lookupWildcardExtends(this, null, typeDecl);
        return lookupWildcardExtends_TypeDecl_value;
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        return super.rewriteTo();
    }
}
