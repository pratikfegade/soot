
package jastadd.soot.JastAddJ;

import java.util.ArrayList;

public interface GenericTypeDecl {
    // Declared in Generics.jrag at line 140

    TypeDecl original();

    // Declared in Generics.jrag at line 141

    int getNumTypeParameter();

    // Declared in Generics.jrag at line 142

    TypeVariable getTypeParameter(int index);

    // Declared in Generics.jrag at line 143

    List getTypeParameterList();

    // Declared in Generics.jrag at line 145

    String fullName();

    // Declared in Generics.jrag at line 146

    String typeName();

    // Declared in Generics.jrag at line 147

    int getNumParTypeDecl();

    // Declared in Generics.jrag at line 148

    ParTypeDecl getParTypeDecl(int index);

    // Declared in Generics.jrag at line 213

  TypeDecl makeGeneric(Signatures.ClassSignature s);


    // Declared in Generics.jrag at line 460


  SimpleSet addTypeVariables(SimpleSet c, String name);


    // Declared in Generics.jrag at line 661

  List createArgumentList(ArrayList params);


    // Declared in Generics.jrag at line 139
 @SuppressWarnings({"unchecked", "cast"})
 boolean isGenericType();
    // Declared in Generics.jrag at line 144
 @SuppressWarnings({"unchecked", "cast"})
 TypeDecl rawType();
    // Declared in Generics.jrag at line 595
 @SuppressWarnings({"unchecked", "cast"})
 TypeDecl lookupParTypeDecl(ParTypeAccess p);
    // Declared in Generics.jrag at line 632
 @SuppressWarnings({"unchecked", "cast"})
 TypeDecl lookupParTypeDecl(ArrayList list);
}
