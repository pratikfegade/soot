package soot.JastAddJ;

import java.util.ArrayList;

/**
 * @ast interface
 */
public interface GenericTypeDecl {


    TypeDecl original();


    int getNumTypeParameter();


    TypeVariable getTypeParameter(int index);


    List getTypeParameterList();


    String fullName();


    String typeName();

    TypeDecl makeGeneric(Signatures.ClassSignature s);

    SimpleSet addTypeVariables(SimpleSet c, String name);

    List createArgumentList(ArrayList params);

    /**
     * @attribute syn
     * @aspect Generics
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:158
     */
    @SuppressWarnings({"unchecked", "cast"}) boolean isGenericType();

    /**
     * @attribute syn
     * @aspect Generics
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:163
     */
    @SuppressWarnings({"unchecked", "cast"}) TypeDecl rawType();

    /**
     * @attribute syn
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:708
     */
    @SuppressWarnings({"unchecked", "cast"}) TypeDecl lookupParTypeDecl(ParTypeAccess p);

    /**
     * @attribute syn
     * @aspect LookupParTypeDecl
     * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:714
     */
    @SuppressWarnings({"unchecked", "cast"}) TypeDecl lookupParTypeDecl(ArrayList list);
}
