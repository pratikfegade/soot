
package jastadd.soot.JastAddJ;

public class AssignDivExpr extends AssignMultiplicativeExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
    public void flushCollectionCache() {
        super.flushCollectionCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public AssignDivExpr clone() throws CloneNotSupportedException {
        AssignDivExpr node = (AssignDivExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public AssignDivExpr copy() {
      try {
          AssignDivExpr node = clone();
          if(children != null) node.children = children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public AssignDivExpr fullCopy() {
        AssignDivExpr res = copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in Expressions.jrag at line 159

  public soot.Value createAssignOp(Body b, soot.Value fst, soot.Value snd) {
    return b.newDivExpr(asImmediate(b, fst), asImmediate(b, snd), this);
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 105

    public AssignDivExpr() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 105
    public AssignDivExpr(Expr p0, Expr p1) {
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

    // Declared in PrettyPrint.jadd at line 249
 @SuppressWarnings({"unchecked", "cast"})     public String printOp() {
        ASTNode$State state = state();
        String printOp_value = printOp_compute();
        return printOp_value;
    }

    private String printOp_compute() {  return " /= ";  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
