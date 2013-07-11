package org.rococoa.okeydoke.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactories;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.internal.Naming;

import java.io.File;
import java.io.IOException;
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

    private final Map<Description, Approver> approvers = new HashMap<Description, Approver>();
    private final ApproverFactory factory;

    private Description description;

    public static TheoryApprovalsRule fileSystemRule(File sourceRoot) {
        return fileSystemRule(sourceRoot, sourceRoot);
    }

    public static TheoryApprovalsRule fileSystemRule(String sourceRoot) {
        return fileSystemRule(new File(sourceRoot));
    }

    public static TheoryApprovalsRule fileSystemRule(final File sourceRoot, final File outDir) {
        return new TheoryApprovalsRule(ApproverFactories.fileSystemApprover(sourceRoot, outDir));
    }

    public static TheoryApprovalsRule fileSystemRule(String sourceRoot, String outDir) {
        return fileSystemRule(new File(sourceRoot), new File(outDir));
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
        if (errors.isEmpty())
            return;
        else
            rethrow(errors.get(0));
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

        @Override
        public void starting(Description description) {
            theory = description;
            if (!approvers.containsKey(description))
                approvers.put(theory, factory.create(Naming.testNameFor(description), TheoryApprovalsRule.this.description.getTestClass()));
            super.starting(description);
        }

        public void lockDown(Object result, Object... arguments) throws IOException {
            Approver approver = approvers.get(theory);
            if (approver == null)
                throw new IllegalStateException("Something's wrong - check that I am an @Rule!");
            approver.writeFormatted(formatted(result, arguments));
        }

        public void lockDownReflectively(Object object, String methodName, Object... arguments) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
            List<Method> methods = findMethods(classFor(object), methodName, arguments);
            for (Method method : methods) {
                try {
                    Object result = method.invoke(object, arguments);
                    lockDown(result, arguments);
                    return;
                } catch (IllegalArgumentException wrongArguments) {
                    // ho hum, try the next
                }
            }
            throw new NoSuchMethodException(methodName);
        }

        private Class<?> classFor(Object object) {
            return object instanceof Class ? (Class<?>) object : object.getClass();
        }

        List<Method> findMethods(Class<?> objectClass, String methodName, Object... arguments) {
            List<Method> result = new ArrayList<Method>(2);

            for (Method method : objectClass.getMethods()) {
                if (methodMatches(method, methodName, arguments))
                    result.add(method);
            }
            return result;
        }

        private boolean methodMatches(Method method, String methodName, Object[] arguments) {
            if (!method.getName().equals(methodName))
                return false;
            return areCompatible(method.getParameterTypes(), arguments);
        }

        private boolean areCompatible(Class[] parameterTypes, Object[] arguments) {
            return parameterTypes.length == arguments.length;
        }

        private String formatted(Object result, Object[] parameters) {
            StringBuilder myResult = new StringBuilder();
            myResult.append("[").append(formatted(parameters)).append("] -> ");
            myResult.append(String.valueOf(result)).append("\n");
            return myResult.toString();
        }

        private String formatted(Object[] parameters) {
            StringBuilder result = new StringBuilder();
            for (Object parameter : parameters) {
                result.append(String.valueOf(parameter)).append(", ");
            }
            return result.substring(0, result.length() - 2);
        }
    }
}
