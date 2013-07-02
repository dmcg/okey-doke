package org.hamcrest.approvals;

import org.junit.runner.Description;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TheoryApprovalsRule extends ApprovalsRule {

    private Map<Description, StringBuilder> results = new HashMap<Description, StringBuilder>();

    public TheoryApprovalsRule(String sourceRoot) {
        super(sourceRoot);
    }

    public TheoryApprover approver() {
        return new TheoryApprover(sourceRoot);
    }

    @Override
    protected void succeeded(Description description) {
        List<Throwable> errors = new ArrayList<Throwable>();
        for (Map.Entry<Description, StringBuilder> entry : results.entrySet()) {
            String actual = entry.getValue().toString();
            String testName = testNameFor(entry.getKey());
            try {
                assertApproved(actual, testName);
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

    public class TheoryApprover extends TestRememberer {

        private Description theory;

        public TheoryApprover(File sourceRoot) {
            super(sourceRoot);
        }

        @Override
        public void starting(Description description) {
            theory = description;
            if (!results.containsKey(description))
                results.put(theory, new StringBuilder());
            super.starting(description);
        }

        public void lockDown(Object result, Object... arguments) {
            results.get(theory).append(formatted(result, arguments));
        }

        public void lockDownReflectively(Object object, String methodName, Object... arguments) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
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

        private Class<? extends Object> classFor(Object object) {
            return object instanceof Class ? (Class<? extends Object>) object : object.getClass();
        }

        List<Method> findMethods(Class<? extends Object> objectClass, String methodName, Object... arguments) {
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
            return result.substring(0, result.length() - 2).toString();
        }
    }
}
