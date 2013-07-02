package org.hamcrest.approvals;

import org.junit.runner.Description;

import java.io.File;

public class Naming {

    public static File dirForPackage(File srcRoot, Object o) {
        return new File(srcRoot, packageFor(o).getName().replaceAll("\\.", "/"));
    }

    private static Package packageFor(Object o) {
        return (o instanceof Class) ? ((Class) o).getPackage() : o.getClass().getPackage();
    }

    public static String testNameFor(Description description) {
        String justTheClassName = description.getTestClass().getSimpleName();
        return justTheClassName + "." + description.getDisplayName().replaceFirst("\\(.*\\)", "");
    }

}
