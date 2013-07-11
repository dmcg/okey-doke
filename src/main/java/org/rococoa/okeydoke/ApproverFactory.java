package org.rococoa.okeydoke;

public interface ApproverFactory {
    public Approver create(String testName, Class<?> testClass);
}
