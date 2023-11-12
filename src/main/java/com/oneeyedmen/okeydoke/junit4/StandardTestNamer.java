package com.oneeyedmen.okeydoke.junit4;

import com.oneeyedmen.okeydoke.Name;
import org.junit.runner.Description;

import java.lang.reflect.Method;

public class StandardTestNamer implements TestNamer {

    @Override
    public String nameFor(Description description) {
        return nameFromClass(description) + suffixFromMethod(description);
    }

    private String suffixFromMethod(Description description) {
        String override = nameFromMethodAnnotation(description);
        if (override != null)
            return "." + override;
        String methodNameFromDescription = description.getMethodName();
        return methodNameFromDescription == null ? "" : ("." + methodNameFromDescription);
    }

    private String nameFromMethodAnnotation(Description description) {
        Method method = methodFrom(description);
        if (method == null)
            return null;
        Name annotation = method.getAnnotation(Name.class);
        return annotation == null ? null : annotation.value();
    }

    private Method methodFrom(Description description) {
        Class<?> testClass = description.getTestClass();
        String methodName = description.getMethodName();
        if (testClass == null || methodName == null)
            return null;
        try {
            return testClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private String nameFromClass(Description description) {
        Class<?> testClass = description.getTestClass();
        if (testClass == null)
            return description.getClassName();
        Name annotation = testClass.getAnnotation(Name.class);
        return annotation == null ? testClass.getSimpleName() : annotation.value();
    }
}
