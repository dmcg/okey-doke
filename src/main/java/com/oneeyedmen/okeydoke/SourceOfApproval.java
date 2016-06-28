package com.oneeyedmen.okeydoke;

import java.io.IOException;

public interface SourceOfApproval {

    public Resource actualFor(String testName) throws IOException;

    public Resource approvedFor(String testName) throws IOException;

    public <T> void checkActualAgainstApproved(String testName, Serializer<T> serializer, Checker<T> checker) throws AssertionError, IOException;

    public void reportFailure(String testName, AssertionError e);

}
