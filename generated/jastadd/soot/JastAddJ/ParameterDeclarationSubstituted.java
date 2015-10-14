/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production ParameterDeclarationSubstituted : {@link ParameterDeclaration} ::= <span class="component">&lt;Original:ParameterDeclaration&gt;</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.ast:33
 */
public class ParameterDeclarationSubstituted extends ParameterDeclaration implements Cloneable {
    /**
     * @apilevel internal
     */
    protected ParameterDeclaration tokenParameterDeclaration_Original;
    /**
     * @apilevel internal
     */
    protected boolean sourceVariableDecl_computed = false;
    /**
     * @apilevel internal
     */
    protected Variable sourceVariableDecl_value;

    /**
     * @ast method
     */
    public ParameterDeclarationSubstituted() {
        super();


    }

    /**
     * @ast method
     */
    public ParameterDeclarationSubstituted(Modifiers p0, Access p1, String p2, ParameterDeclaration p3) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setOriginal(p3);
    }

    /**
     * @ast method
     */
    public ParameterDeclarationSubstituted(Modifiers p0, Access p1, beaver.Symbol p2, ParameterDeclaration p3) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setOriginal(p3);
    }

    /**
     * @apilevel low-level
     */
    public void flushCache() {
        super.flushCache();
        sourceVariableDecl_computed = false;
        sourceVariableDecl_value = null;
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
    public ParameterDeclarationSubstituted clone() throws CloneNotSupportedException {
        ParameterDeclarationSubstituted node = (ParameterDeclarationSubstituted) super.clone();
        node.sourceVariableDecl_computed = false;
        node.sourceVariableDecl_value = null;
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }

    /**
     * @apilevel internal
     */
    @SuppressWarnings({"unchecked", "cast"})
    public ParameterDeclarationSubstituted copy() {
        try {
            ParameterDeclarationSubstituted node = clone();
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
    public ParameterDeclarationSubstituted fullCopy() {
        ParameterDeclarationSubstituted tree = copy();
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
     * Retrieves the Modifiers child.
     *
     * @return The current node used as the Modifiers child.
     * @apilevel high-level
     * @ast method
     */
    public Modifiers getModifiers() {
        return (Modifiers) getChild(0);
    }

    /**
     * Replaces the Modifiers child.
     *
     * @param node The new node to replace the Modifiers child.
     * @apilevel high-level
     * @ast method
     */
    public void setModifiers(Modifiers node) {
        setChild(node, 0);
    }

    /**
     * Retrieves the Modifiers child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the Modifiers child.
     * @apilevel low-level
     * @ast method
     */
    public Modifiers getModifiersNoTransform() {
        return (Modifiers) getChildNoTransform(0);
    }

    /**
     * Retrieves the TypeAccess child.
     *
     * @return The current node used as the TypeAccess child.
     * @apilevel high-level
     * @ast method
     */
    public Access getTypeAccess() {
        return (Access) getChild(1);
    }

    /**
     * Replaces the TypeAccess child.
     *
     * @param node The new node to replace the TypeAccess child.
     * @apilevel high-level
     * @ast method
     */
    public void setTypeAccess(Access node) {
        setChild(node, 1);
    }

    /**
     * Retrieves the TypeAccess child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the TypeAccess child.
     * @apilevel low-level
     * @ast method
     */
    public Access getTypeAccessNoTransform() {
        return (Access) getChildNoTransform(1);
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
     * @apilevel internal
     * @ast method
     *
     */

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
     * Retrieves the value for the lexeme Original.
     *
     * @return The value for the lexeme Original.
     * @apilevel high-level
     * @ast method
     */
    public ParameterDeclaration getOriginal() {
        return tokenParameterDeclaration_Original;
    }

    /**
     * Replaces the lexeme Original.
     *
     * @param value The new value for the lexeme Original.
     * @apilevel high-level
     * @ast method
     */
    public void setOriginal(ParameterDeclaration value) {
        tokenParameterDeclaration_Original = value;
    }

    /**
     * @attribute syn
     * @aspect SourceDeclarations
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1524
     */
    @SuppressWarnings({"unchecked", "cast"})
    public Variable sourceVariableDecl() {
        if (sourceVariableDecl_computed) {
            return sourceVariableDecl_value;
        }
        ASTNode$State state = state();
        int num = state.boundariesCrossed;
        boolean isFinal = this.is$Final();
        sourceVariableDecl_value = sourceVariableDecl_compute();
        if (isFinal && num == state().boundariesCrossed) sourceVariableDecl_computed = true;
        return sourceVariableDecl_value;
    }

    /**
     * @apilevel internal
     */
    private Variable sourceVariableDecl_compute() {
        return getOriginal().sourceVariableDecl();
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        return super.rewriteTo();
    }
}
