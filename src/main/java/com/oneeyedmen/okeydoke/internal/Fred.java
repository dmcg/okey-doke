package com.oneeyedmen.okeydoke.internal;

import org.jmock.api.Invocation;
import org.jmock.api.Invokable;
import org.jmock.lib.legacy.ClassImposteriser;

import java.lang.reflect.InvocationHandler;

public class Fred {

    private static final org.jmock.api.Imposteriser IMPOSTERISER = loadImposteriser();

    private static org.jmock.api.Imposteriser loadImposteriser() {
        try {
            return ClassImposteriser.INSTANCE;
        } catch (Throwable t) {
            throw new ExceptionInInitializerError("You need JMock and JMock-Legacy in your classpath to do this");
        }
    }

    public static <T> T newProxyInstance(Class<T> type, final InvocationHandler handler)
    {
        Invokable invokable = new Invokable() {
            @Override
            public Object invoke(Invocation invocation) throws Throwable {
                return handler.invoke(null, invocation.getInvokedMethod(), invocation.getParametersAsArray());
            }
        };
        return IMPOSTERISER.imposterise(invokable, type);
    }
}