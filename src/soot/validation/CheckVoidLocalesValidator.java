package soot.validation;

import soot.Body;
import soot.Local;
import soot.VoidType;

import java.util.List;

public enum CheckVoidLocalesValidator implements BodyValidator {
    INSTANCE;

    public static CheckVoidLocalesValidator v() {
        return INSTANCE;
    }


    @Override
    public void validate(Body body, List<ValidationException> exception) {
        for (Local l : body.getLocals()) {
            if (l.getType() instanceof VoidType)
                exception.add(new ValidationException(l, "Local " + l + " in " + body.getMethod() + " defined with void type"));
        }
    }


    @Override
    public boolean isBasicValidator() {
        return false;
    }
}
