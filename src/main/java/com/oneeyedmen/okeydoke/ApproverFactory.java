package com.oneeyedmen.okeydoke;

public interface ApproverFactory<A extends BaseApprover<?,?>> {
    public A createApprover(String testName, Class<?> testClass);
}
