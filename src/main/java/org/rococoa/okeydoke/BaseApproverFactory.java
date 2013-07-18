package org.rococoa.okeydoke;

public interface BaseApproverFactory<T, A extends BaseApprover<T,?,?>> {
    public A create(String testName, Class<?> testClass);
}
