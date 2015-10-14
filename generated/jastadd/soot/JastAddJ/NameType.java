package soot.JastAddJ;

/**
 * @ast class
 */
public class NameType extends java.lang.Object {

    public static final NameType NO_NAME = new NameType();
    public static final NameType PACKAGE_NAME = new NameType() {
        public Access reclassify(String name, int start, int end) {
            return new PackageAccess(name, start, end);
        }
    };
    public static final NameType TYPE_NAME = new NameType() {
        public Access reclassify(String name, int start, int end) {
            return new TypeAccess(name, start, end);
        }
    };
    public static final NameType PACKAGE_OR_TYPE_NAME = new NameType() {
        public Access reclassify(String name, int start, int end) {
            return new PackageOrTypeAccess(name, start, end);
        }
    };
    public static final NameType AMBIGUOUS_NAME = new NameType() {
        public Access reclassify(String name, int start, int end) {
            return new AmbiguousAccess(name, start, end);
        }
    };
    public static final NameType METHOD_NAME = new NameType();
    public static final NameType ARRAY_TYPE_NAME = new NameType();
    public static final NameType ARRAY_READ_NAME = new NameType();
    public static final NameType EXPRESSION_NAME = new NameType() {
        public Access reclassify(String name, int start, int end) {
            return new VarAccess(name, start, end);
        }
    };


    private NameType() {
        super();
    }

    public Access reclassify(String name, int start, int end) {
        throw new Error("Can not reclassify ParseName node " + name);
    }


}
