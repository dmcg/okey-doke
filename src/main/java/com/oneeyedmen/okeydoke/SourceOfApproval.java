package com.oneeyedmen.okeydoke;

import java.io.IOException;

public interface SourceOfApproval {

    public Resource actualFor(String testName);

    public Resource approvedFor(String testName);

    public <T> void checkActualAgainstApproved(String testName, Serializer<T> serializer, Checker<T> checker) throws AssertionError, IOException;

    public void reportFailure(String testName, AssertionError e);

}
