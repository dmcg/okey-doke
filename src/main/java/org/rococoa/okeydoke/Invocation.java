package org.rococoa.okeydoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invocation {

    public final Object[] arguments;
    public final Object result;

    public Invocation(Object o, Method method, Object[] arguments) throws InvocationTargetException, IllegalAccessException {
        this.arguments = arguments;
        result = method.invoke(o, arguments);
    }

    public Invocation(Object[] arguments, Object result) {
        this.arguments = arguments;
        this.result = result;
    }
}
