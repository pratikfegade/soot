/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

/**
 * @production MinusExpr : {@link Unary};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:139
 */
public class MinusExpr extends Unary implements Cloneable {
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
    public MinusExpr() {
        super();


    }

    /**
     * @ast method
     */
    public MinusExpr(Expr p0) {
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
    public MinusExpr clone() throws CloneNotSupportedException {
        MinusExpr node = (MinusExpr) super.clone();
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
    public MinusExpr copy() {
        try {
            MinusExpr node = clone();
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
    public MinusExpr fullCopy() {
        MinusExpr tree = copy();
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
     * @aspect TypeCheck
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeCheck.jrag:275
     */
    public void typeCheck() {
        if (!getOperand().type().isNumericType())
            error("unary minus only operates on numeric types");
    }

    /**
     * @ast method
     * @aspect Expressions
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/Expressions.jrag:692
     */
    public soot.Value eval(Body b) {
        return b.newNegExpr(asImmediate(b, getOperand().eval(b)), this);
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
        return true;
    }

    /**
     * Retrieves the Operand child.
     *
     * @return The current node used as the Operand child.
     * @apilevel high-level
     * @ast method
     */
    public Expr getOperand() {
        return (Expr) getChild(0);
    }

    /**
     * Replaces the Operand child.
     *
     * @param node The new node to replace the Operand child.
     * @apilevel high-level
     * @ast method
     */
    public void setOperand(Expr node) {
        setChild(node, 0);
    }

    /**
     * Retrieves the Operand child.
     * <p><em>This method does not invoke AST transformations.</em></p>
     *
     * @return The current node used as the Operand child.
     * @apilevel low-level
     * @ast method
     */
    public Expr getOperandNoTransform() {
        return (Expr) getChildNoTransform(0);
    }

    /**
     * @attribute syn
     * @aspect ConstantExpression
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:91
     */
    public Constant constant() {
        ASTNode$State state = state();
        try {
            return type().minus(getOperand().constant());
        } finally {
        }
    }

    /**
     * @attribute syn
     * @aspect ConstantExpression
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:336
     */
    public boolean isConstant() {
        ASTNode$State state = state();
        try {
            return getOperand().isConstant();
        } finally {
        }
    }

    /**
     * @attribute syn
     * @aspect PrettyPrint
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:376
     */
    public String printPreOp() {
        ASTNode$State state = state();
        try {
            return "-";
        } finally {
        }
    }

    /**
     * @attribute syn
     * @aspect TypeAnalysis
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:316
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
        return getOperand().type().unaryNumericPromotion();
    }

    /**
     * @apilevel internal
     */
    public ASTNode rewriteTo() {
        // Declared in /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag at line 341
        if (getOperand() instanceof IntegerLiteral && ((IntegerLiteral) getOperand()).isDecimal() && getOperand().isPositive()) {
            state().duringLiterals++;
            ASTNode result = rewriteRule0();
            state().duringLiterals--;
            return result;
        }

        // Declared in /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag at line 353
        if (getOperand() instanceof LongLiteral && ((LongLiteral) getOperand()).isDecimal() && getOperand().isPositive()) {
            state().duringLiterals++;
            ASTNode result = rewriteRule1();
            state().duringLiterals--;
            return result;
        }

        return super.rewriteTo();
    }

    /**
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag:341
     * @apilevel internal
     */
    private IntegerLiteral rewriteRule0() {
        {
            IntegerLiteral original = (IntegerLiteral) getOperand();
            IntegerLiteral literal = new IntegerLiteral("-" +
                    original.getLITERAL());
            literal.setDigits(original.getDigits());
            literal.setKind(original.getKind());
            return literal;
        }
    }

    /**
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag:353
     * @apilevel internal
     */
    private LongLiteral rewriteRule1() {
        {
            LongLiteral original = (LongLiteral) getOperand();
            LongLiteral literal = new LongLiteral("-" +
                    original.getLITERAL());
            literal.setDigits(original.getDigits());
            literal.setKind(original.getKind());
            return literal;
        }
    }
}
