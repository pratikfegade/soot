
package jastadd.soot.JastAddJ;

import java.util.Collection;
import java.util.HashMap;

public interface MemberSubstitutor extends Parameterization {
    // Declared in Generics.jrag at line 680

    TypeDecl original();

    // Declared in Generics.jrag at line 681

    void addBodyDecl(BodyDecl b);

    // Declared in Generics.jrag at line 682

    TypeDecl substitute(TypeVariable typeVariable);

    // Declared in Generics.jrag at line 929
 @SuppressWarnings({"unchecked", "cast"})
 HashMap localMethodsSignatureMap();
    // Declared in Generics.jrag at line 944
 @SuppressWarnings({"unchecked", "cast"})
 SimpleSet localFields(String name);
    // Declared in Generics.jrag at line 959
 @SuppressWarnings({"unchecked", "cast"})
 SimpleSet localTypeDecls(String name);
    // Declared in Generics.jrag at line 989
 @SuppressWarnings({"unchecked", "cast"})
 Collection constructors();
}
