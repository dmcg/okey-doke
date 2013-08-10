package org.rococoa.okeydoke;

import java.io.File;

public class Approver extends BaseApprover<Object, String, File> {

    public Approver(String testName, SourceOfApproval<File> sourceOfApproval, Reporter<File> reporter) {
        this(testName, sourceOfApproval, Formatters.stringFormatter(), Serializers.stringSerializer(), Checkers.stringChecker(), reporter);
    }

    public Approver(String testName,
                    SourceOfApproval<File> sourceOfApproval,
                    Formatter<Object, String> formatter,
                    Serializer<String> serializer,
                    Checker<String> checker,
                    Reporter<File> reporter) {
        super(testName, sourceOfApproval, formatter, serializer, checker, reporter);
    }

    public Transcript transcript() {
        return new StandardTranscript(printStream(), formatter());
    }
}
