package com.oneeyedmen.okeydoke.junit;

import org.junit.runner.Description;

public class StandardTestNamer implements TestNamer {

    @Override
    public String nameFor(Description description) {
        String justTheClassName = description.getTestClass().getSimpleName();
        String methodName = description.getMethodName();
        return methodName == null ?
                justTheClassName :
                justTheClassName + "." + methodName;
    }
}
