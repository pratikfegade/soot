package soot.tagkit;

import soot.util.Switch;

public interface IAnnotationElemTypeSwitch extends Switch {
    void caseAnnotationAnnotationElem(AnnotationAnnotationElem v);

    void caseAnnotationArrayElem(AnnotationArrayElem v);

    void caseAnnotationBooleanElem(AnnotationBooleanElem v);

    void caseAnnotationClassElem(AnnotationClassElem v);

    void caseAnnotationDoubleElem(AnnotationDoubleElem v);

    void caseAnnotationEnumElem(AnnotationEnumElem v);

    void caseAnnotationFloatElem(AnnotationFloatElem v);

    void caseAnnotationIntElem(AnnotationIntElem v);

    void caseAnnotationLongElem(AnnotationLongElem v);

    void caseAnnotationStringElem(AnnotationStringElem v);

    void defaultCase(Object object);

}
