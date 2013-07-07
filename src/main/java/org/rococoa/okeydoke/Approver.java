package org.rococoa.okeydoke;

public class Approver extends BaseApprover<String> {

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        super(testName, sourceOfApproval, Formatters.stringFormatter());
    }
}
