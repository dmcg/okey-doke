package org.rococoa.okeydoke;

import java.io.IOException;

public class Approver extends BaseApprover<Object, String> {

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        super(testName, sourceOfApproval, Formatters.stringFormatter());
    }

    public Transcript transcript() throws IOException {
        return new StandardTranscript(printStream(), formatter());
    }
}
