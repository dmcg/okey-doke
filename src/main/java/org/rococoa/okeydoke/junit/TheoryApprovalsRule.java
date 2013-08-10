package org.rococoa.okeydoke.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.*;
import org.rococoa.okeydoke.formatters.InvocationFormatter;
import org.rococoa.okeydoke.internal.Fred;
import org.rococoa.okeydoke.internal.MethodFinder;
import org.rococoa.okeydoke.internal.Naming;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use as an @ClassRule to automate approval of @Theories in JUnit
 */
public class TheoryApprovalsRule extends TestWatcher {

    private final MethodFinder methodFinder = new MethodFinder();

    private final Map<Description, Approver> approvers = new HashMap<Description, Approver>();
    private final ApproverFactory factory;

    private Description description;

    public static TheoryApprovalsRule fileSystemRule(File sourceRoot) {
        return new TheoryApprovalsRule(ApproverFactories.fileSystemApprover(sourceRoot));
    }

    public static TheoryApprovalsRule fileSystemRule(String sourceRoot) {
        return fileSystemRule(new File(sourceRoot));
    }

    public TheoryApprovalsRule(ApproverFactory factory) {
        this.factory = factory;
    }

    public TheoryApprover approver() {
        return new TheoryApprover();
    }

    public void starting(Description description) {
        this.description = description;
    }

    @Override
    protected void succeeded(Description description) {
        List<Throwable> errors = new ArrayList<Throwable>();
        for (Approver approver : approvers.values()) {
            try {
                approver.assertSatisfied();
            } catch (Throwable t) {
                errors.add(t);
            }
        }
        if (!errors.isEmpty()) {
            rethrow(errors.get(0));
        }
    }

    private void rethrow(Throwable t) {
        if (t instanceof Error)
            throw (Error) t;
        else if (t instanceof RuntimeException)
            throw (RuntimeException) t;
        else throw new RuntimeException(t);
    }

    public class TheoryApprover extends TestWatcher {

        private Description theory;
        private Formatter<Object, String> invocationFormatter = Formatters.invocationFormatter();

        public TheoryApprover withInvocationFormatter(InvocationFormatter invocationFormatter) {
            this.invocationFormatter = invocationFormatter;
            return this;
        }

        @Override
        public void starting(Description description) {
            theory = description;
            if (!approvers.containsKey(description))
                approvers.put(theory, factory.create(Naming.testNameFor(description), TheoryApprovalsRule.this.description.getTestClass()));
            super.starting(description);
        }

        public void lockDownResult(Object result, Object... arguments) {
            Invocation invocation = new Invocation(arguments, result);
            lockDownResult(invocation);
        }

        public void lockDownResult(Invocation invocation) {
            Approver approver = approvers.get(theory);
            if (approver == null)
                throw new IllegalStateException("Something is wrong - check that I am an @Rule!");
            approver.transcript().appendFormatted(invocation, invocationFormatter).endl();
        }

        public void lockDownReflectively(Object object, String methodName, Object... arguments) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            List<Method> methods = methodFinder.findMethods(methodFinder.classFor(object), methodName, arguments);
            for (Method method : methods) {
                try {
                    lockDownResult(new Invocation(object, method, arguments));
                    return;
                } catch (IllegalArgumentException wrongArguments) {
                    // ho hum, try the next
                }
            }
            throw new NoSuchMethodException(methodName);
        }

        // experimental
        public <T> T lockDown(final T object) {
            InvocationHandler handler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Object result = method.invoke(object, args);
                    lockDownResult(result, args);
                    return result;
                }
            };
            return (T) Fred.newProxyInstance(object.getClass(), handler);
        }

    }

}
