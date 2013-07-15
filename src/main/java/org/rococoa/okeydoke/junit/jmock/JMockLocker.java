package org.rococoa.okeydoke.junit.jmock;

import org.jmock.api.Imposteriser;
import org.jmock.api.Invocation;
import org.jmock.api.Invokable;
import org.jmock.lib.legacy.ClassImposteriser;
import org.rococoa.okeydoke.junit.TheoryApprovalsRule;

public class JMockLocker {

    private static final Imposteriser IMPOSTERISER = loadImposteriser();

    private static Imposteriser loadImposteriser() {
        try {
            return ClassImposteriser.INSTANCE;
        } catch (Throwable t) {
            throw new ExceptionInInitializerError("You need JMock and JMock-Legacy in your classpath to do this");
        }
    }

    public static <T> T lockWithJMock(final T object, final TheoryApprovalsRule.TheoryApprover approver) {
        Invokable invokable = new Invokable() {
            @Override
            public Object invoke(Invocation invocation) throws Throwable {
                Object result = invocation.getInvokedMethod().invoke(object, invocation.getParametersAsArray());
                approver.lockDownResult(result, invocation.getParametersAsArray());
                return result;
            }
        };
        return (T) IMPOSTERISER.imposterise(invokable, object.getClass());
    }
}
