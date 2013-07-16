package org.rococoa.okeydoke;

import java.io.File;
import java.io.IOException;

public class Approver extends BaseApprover<Object, String, File> {

    public Approver(String testName, SourceOfApproval sourceOfApproval, Reporter<File> reporter) {
        super(testName, sourceOfApproval, Formatters.stringFormatter(), reporter);
    }

    public Transcript transcript() throws IOException {
        return new StandardTranscript(printStream(), formatter());
    }
}
