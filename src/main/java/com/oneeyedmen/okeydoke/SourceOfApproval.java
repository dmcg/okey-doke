package com.oneeyedmen.okeydoke;

import java.io.IOException;
import java.io.OutputStream;

public interface SourceOfApproval {

    public OutputStream outputForActual(String testName) throws IOException;

    public void removeActual(String testName) throws IOException;

    public void reportFailure(String testName, AssertionError e);

    public <T> void writeToApproved(String testName, T thing, Serializer<T> serializer) throws IOException;

    public <T> T readActual(String testName, Serializer<T> serializer) throws IOException;

    public <T> void checkActualAgainstApproved(String testName, Serializer<T> serializer, Checker<T> checker) throws AssertionError, IOException;
}
