package com.oneeyedmen.okeydoke;

import java.io.File;

/**
 * A BinaryApprover is used to write raw bytes to file.
 */
public class BinaryApprover extends BaseApprover<byte[], byte[], File> {

    public BinaryApprover(String testname, SourceOfApproval<File> sourceOfApproval) {
        super(testname, sourceOfApproval, Formatters.binaryFormatter(), Serializers.binarySerializer(), Checkers.hexChecker());
    }
}
