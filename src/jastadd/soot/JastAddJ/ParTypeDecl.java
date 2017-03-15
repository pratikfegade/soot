
package jastadd.soot.JastAddJ;

import java.util.ArrayList;
import java.util.HashMap;

public interface ParTypeDecl extends Parameterization {
    // Declared in Generics.jrag at line 220

    //syn String name();
    int getNumArgument();

    // Declared in Generics.jrag at line 221

    Access getArgument(int index);

    // Declared in Generics.jrag at line 224

    String typeName();

    // Declared in Generics.jrag at line 225

    SimpleSet localFields(String name);

    // Declared in Generics.jrag at line 226

    HashMap localMethodsSignatureMap();

    // Declared in Generics.jrag at line 697

  TypeDecl substitute(TypeVariable typeVariable);


    // Declared in Generics.jrag at line 710


  int numTypeParameter();


    // Declared in Generics.jrag at line 713

  TypeVariable typeParameter(int index);


    // Declared in Generics.jrag at line 745

  Access substitute(Parameterization parTypeDecl);


    // Declared in GenericsParTypeDecl.jrag at line 73


  Access createQualifiedAccess();


    // Declared in GenericsCodegen.jrag at line 406


  void transformation();


    // Declared in Generics.jrag at line 222
 @SuppressWarnings({"unchecked", "cast"})
 boolean isParameterizedType();
    // Declared in Generics.jrag at line 223
 @SuppressWarnings({"unchecked", "cast"})
 boolean isRawType();
    // Declared in Generics.jrag at line 351
 @SuppressWarnings({"unchecked", "cast"})
 boolean sameArgument(ParTypeDecl decl);
    // Declared in Generics.jrag at line 548
 @SuppressWarnings({"unchecked", "cast"})
 boolean sameSignature(Access a);
    // Declared in Generics.jrag at line 583
 @SuppressWarnings({"unchecked", "cast"})
 boolean sameSignature(ArrayList list);
    // Declared in GenericsParTypeDecl.jrag at line 30
 @SuppressWarnings({"unchecked", "cast"})
 String nameWithArgs();
    // Declared in GenericsParTypeDecl.jrag at line 45
    TypeDecl genericDecl();
}
