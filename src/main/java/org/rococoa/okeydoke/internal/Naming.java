package org.rococoa.okeydoke.internal;

import org.junit.runner.Description;

public class Naming {

    public static String testNameFor(Description description) {
        String justTheClassName = description.getTestClass().getSimpleName();
        String methodName = description.getMethodName();
        return methodName == null ?
                justTheClassName :
                justTheClassName + "." + methodName;
    }

}
