package com.oneeyedmen.okeydoke;

import java.io.IOException;
import java.io.OutputStream;

public interface SourceOfApproval {

    public Resource resourceFor(String testName) throws IOException;

    public void reportFailure(String testName, AssertionError e);

    public <T> void writeToApproved(String testName, T thing, Serializer<T> serializer) throws IOException;

    public <T> T actualContentOrNull(String testName, Serializer<T> serializer) throws IOException;

    public <T> void checkActualAgainstApproved(OutputStream outputStream, String testName, Serializer<T> serializer, Checker<T> checker) throws IOException;

    public void removeApproved(String testName) throws IOException;
}
