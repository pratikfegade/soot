
package jastadd.soot.JastAddJ;

public abstract class EqualityExpr extends RelationalExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
    public void flushCollectionCache() {
        super.flushCollectionCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public EqualityExpr clone() throws CloneNotSupportedException {
        EqualityExpr node = (EqualityExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }
    // Declared in TypeCheck.jrag at line 220


  // 15.21
  public void typeCheck() {
    TypeDecl left = getLeftOperand().type();
    TypeDecl right = getRightOperand().type();
    if(left.isNumericType() && right.isNumericType())
      return;
    else if(left.isBoolean() && right.isBoolean())
      return;
    else if((left.isReferenceType() || left.isNull()) && (right.isReferenceType() || right.isNull())) {
      if(left.castingConversionTo(right) || right.castingConversionTo(left))
        return;
    }
    error(left.typeName() + " can not be compared to " + right.typeName());
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 184

    public EqualityExpr() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 184
    public EqualityExpr(Expr p0, Expr p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    // Declared in java.ast at line 15


  protected int numChildren() {
    return 2;
  }

    // Declared in java.ast at line 18

    public boolean mayHaveRewrite() {
        return false;
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 153
    public void setLeftOperand(Expr node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Expr getLeftOperand() {
        return (Expr)getChild(0);
    }

    // Declared in java.ast at line 9


    public Expr getLeftOperandNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 153
    public void setRightOperand(Expr node) {
        setChild(node, 1);
    }

    // Declared in java.ast at line 5

    public Expr getRightOperand() {
        return (Expr)getChild(1);
    }

    // Declared in java.ast at line 9


    public Expr getRightOperandNoTransform() {
        return (Expr)getChildNoTransform(1);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
