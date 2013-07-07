package org.rococoa.okeydoke;

public class BinaryApprover extends BaseApprover<byte[]> {
    public BinaryApprover(String testname, SourceOfApproval sourceOfApproval) {
        super(testname, sourceOfApproval, Formatters.binaryFormatter());
    }
}
