package soot.JastAddJ;

import java.util.Collection;
import java.util.HashMap;

/**
 * @ast interface
 */
public interface MemberSubstitutor extends Parameterization {


    TypeDecl original();


    void addBodyDecl(BodyDecl b);


    TypeDecl substitute(TypeVariable typeVariable);

    /**
     * @attribute syn
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1084
     */
    @SuppressWarnings({"unchecked", "cast"}) HashMap localMethodsSignatureMap();

    /**
     * @attribute syn
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1119
     */
    @SuppressWarnings({"unchecked", "cast"}) SimpleSet localFields(String name);

    /**
     * @attribute syn
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1154
     */
    @SuppressWarnings({"unchecked", "cast"}) SimpleSet localTypeDecls(String name);

    /**
     * @attribute syn
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1213
     */
    @SuppressWarnings({"unchecked", "cast"}) Collection constructors();
}
