package com.oneeyedmen.okeydoke;

/**
 * A BinaryApprover is used to write raw bytes to file.
 */
public class BinaryApprover extends BaseApprover<byte[], byte[]> {

    public BinaryApprover(String testname, SourceOfApproval sourceOfApproval) {
        super(testname, sourceOfApproval, Formatters.binaryFormatter(), Serializers.binarySerializer(), Checkers.hexChecker());
    }
}
