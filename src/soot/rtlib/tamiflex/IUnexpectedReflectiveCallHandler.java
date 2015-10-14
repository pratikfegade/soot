package soot.rtlib.tamiflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public interface IUnexpectedReflectiveCallHandler {

    void classNewInstance(Class<?> c);

    void classForName(String typeName);

    void constructorNewInstance(Constructor<?> c);

    void methodInvoke(Object receiver, Method m);

    void fieldSet(Object receiver, Field f);

    void fieldGet(Object receiver, Field f);

}