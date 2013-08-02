package org.rococoa.okeydoke;

import java.io.File;

public class Approver extends BaseApprover<Object, String, File> {

    public Approver(String testName, SourceOfApproval<File> sourceOfApproval, Reporter<File> reporter) {
        this(testName, sourceOfApproval, Formatters.stringFormatter(), reporter);
    }

    public Approver(String testName, SourceOfApproval<File> sourceOfApproval, Formatter<Object, String> formatter, Reporter<File> reporter) {
        super(testName, sourceOfApproval, formatter, reporter);
    }

    public Transcript transcript() {
        return new StandardTranscript(printStream(), formatter());
    }
}
