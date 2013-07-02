package org.hamcrest.approvals;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;

public class TestRememberer extends TestWatcher {

    protected final File sourceRoot;

    protected String testName;
    protected Class<?> testClass;

    public TestRememberer(File sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    public static File dirForPackage(File srcRoot, Object o) {
        return new File(srcRoot, packageFor(o).getName().replaceAll("\\.", "/"));
    }

    private static Package packageFor(Object o) {
        return (o instanceof Class) ? ((Class) o).getPackage() : o.getClass().getPackage();
    }

    @Override
    public void starting(org.junit.runner.Description description) {
        testName = testNameFor(description);
        testClass = description.getTestClass();
    }

    protected String testNameFor(Description description) {
        String justTheClassName = description.getTestClass().getSimpleName();
        return justTheClassName + "." + description.getDisplayName().replaceFirst("\\(.*\\)", "");
    }

    public String testName() {
        return testName;
    }
}
