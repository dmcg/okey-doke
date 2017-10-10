package com.oneeyedmen.okeydoke.junit;

import com.oneeyedmen.okeydoke.Name;
import org.junit.runner.Description;

public class StandardTestNamer implements TestNamer {

    @Override
    public String nameFor(Description description) {
        return nameFromClass(description) + nameFromMethod(description);
    }

    private String nameFromMethod(Description description) {
        String methodName = description.getMethodName();
        return methodName == null ? "" : "." + methodName;
    }

    private String nameFromClass(Description description) {
        Class<?> testClass = description.getTestClass();
        if (testClass == null)
            return "";
        Name annotation = testClass.getAnnotation(Name.class);
        return annotation == null ? testClass.getSimpleName() : annotation.value();
    }
}
