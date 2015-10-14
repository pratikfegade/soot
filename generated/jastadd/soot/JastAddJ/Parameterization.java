package soot.JastAddJ;

/**
 * @ast interface
 */
public interface Parameterization {


    boolean isRawType();


    TypeDecl substitute(TypeVariable typeVariable);
}
