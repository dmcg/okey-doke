package com.oneeyedmen.okeydoke.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodFinder {

    public Class<?> classFor(Object object) {
        return object instanceof Class ? (Class<?>) object : object.getClass();
    }

    public List<Method> findMethods(Class<?> objectClass, String methodName, Object... arguments) {
        List<Method> result = new ArrayList<Method>(2);

        for (Method method : objectClass.getMethods()) {
            if (methodMatches(method, methodName, arguments))
                result.add(method);
        }
        return result;
    }

    protected boolean methodMatches(Method method, String methodName, Object[] arguments) {
        if (!method.getName().equals(methodName))
            return false;
        return areCompatible(method.getParameterTypes(), arguments);
    }

    protected boolean areCompatible(Class[] parameterTypes, Object[] arguments) {
        return parameterTypes.length == arguments.length;
    }
}
