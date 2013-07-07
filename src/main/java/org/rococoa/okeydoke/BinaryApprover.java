package org.rococoa.okeydoke;

public class BinaryApprover extends Approver {
    public BinaryApprover(String testname, SourceOfApproval sourceOfApproval) {
        super(testname, sourceOfApproval, Formatters.binaryFormatter());
    }
}
