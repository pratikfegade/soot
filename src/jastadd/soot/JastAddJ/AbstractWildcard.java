
package jastadd.soot.JastAddJ;

public abstract class AbstractWildcard extends Access implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
    public void flushCollectionCache() {
        super.flushCollectionCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public AbstractWildcard clone() throws CloneNotSupportedException {
        AbstractWildcard node = (AbstractWildcard)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
        return node;
    }
    // Declared in Generics.ast at line 3
    // Declared in Generics.ast line 17

    public AbstractWildcard() {
        super();


    }

    // Declared in Generics.ast at line 9


  protected int numChildren() {
    return 0;
  }

    // Declared in Generics.ast at line 12

    public boolean mayHaveRewrite() {
        return false;
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
