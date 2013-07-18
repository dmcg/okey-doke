package org.rococoa.okeydoke;

public interface ApproverFactory extends BaseApproverFactory<Object, Approver> {
    @Override public Approver create(String testName, Class<?> testClass);
}
