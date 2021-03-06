package soot.asm.backend.targets;

import javax.annotation.Generated;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Generated(value="forTesting")
public @interface MyTestAnnotation {
	int iVal();
	float fVal();
	long lVal();
	double dVal();
	boolean zVal();
	byte bVal();
	short sVal();
	String strVal();
	Class<AnnotatedClass> rVal();
	int[] iAVal();
	String [] sAVal();
}
