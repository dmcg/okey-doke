package org.rococoa.okeydoke;

import java.io.File;

public class Approver extends BaseApprover<Object, String, File> {

    public Approver(String testName, SourceOfApproval<File> sourceOfApproval) {
        this(testName,
                sourceOfApproval,
                Formatters.stringFormatter(),
                Serializers.stringSerializer(),
                Checkers.stringChecker()
        );
    }

    public Approver(String testName,
                    SourceOfApproval<File> sourceOfApproval,
                    Formatter<Object, String> formatter,
                    Serializer<String> serializer,
                    Checker<String> checker) {
        super(testName, sourceOfApproval, formatter, serializer, checker);
    }

    public Transcript transcript() {
        return new StandardTranscript(printStream(), formatter());
    }
}
