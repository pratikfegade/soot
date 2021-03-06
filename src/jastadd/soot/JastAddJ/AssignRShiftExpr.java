
package jastadd.soot.JastAddJ;

public class AssignRShiftExpr extends AssignShiftExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
    public void flushCollectionCache() {
        super.flushCollectionCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public AssignRShiftExpr clone() throws CloneNotSupportedException {
        AssignRShiftExpr node = (AssignRShiftExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public AssignRShiftExpr copy() {
      try {
          AssignRShiftExpr node = clone();
          if(children != null) node.children = children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public AssignRShiftExpr fullCopy() {
        AssignRShiftExpr res = copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in Expressions.jrag at line 148

  public soot.Value eval(Body b) { return emitShiftExpr(b); }

    // Declared in Expressions.jrag at line 174

  public soot.Value createAssignOp(Body b, soot.Value fst, soot.Value snd) {
    return b.newShrExpr(asImmediate(b, fst), asImmediate(b, snd), this);
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 114

    public AssignRShiftExpr() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 114
    public AssignRShiftExpr(Expr p0, Expr p1) {
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
    // Declared in java.ast line 99
    public void setDest(Expr node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Expr getDest() {
        return (Expr)getChild(0);
    }

    // Declared in java.ast at line 9


    public Expr getDestNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 99
    public void setSource(Expr node) {
        setChild(node, 1);
    }

    // Declared in java.ast at line 5

    public Expr getSource() {
        return (Expr)getChild(1);
    }

    // Declared in java.ast at line 9


    public Expr getSourceNoTransform() {
        return (Expr)getChildNoTransform(1);
    }

    // Declared in PrettyPrint.jadd at line 254
 @SuppressWarnings({"unchecked", "cast"})     public String printOp() {
        ASTNode$State state = state();
        String printOp_value = printOp_compute();
        return printOp_value;
    }

    private String printOp_compute() {  return " >>= ";  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
