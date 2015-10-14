package soot.validation;

import soot.SootClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validates classes to make sure that the outer class chain is not recursive
 *
 * @author Steven Arzt
 */
public enum OuterClassValidator implements ClassValidator {
    INSTANCE;

    public static OuterClassValidator v() {
        return INSTANCE;
    }

    @Override
    public void validate(SootClass sc, List<ValidationException> exceptions) {
        Set<SootClass> outerClasses = new HashSet<SootClass>();
        SootClass curClass = sc;
        while (curClass != null) {
            if (!outerClasses.add(curClass)) {
                exceptions.add(new ValidationException(curClass, "Circular outer class chain"));
                break;
            }
            curClass = curClass.hasOuterClass() ? curClass.getOuterClass() : null;
        }
    }

    @Override
    public boolean isBasicValidator() {
        return true;
    }

}
