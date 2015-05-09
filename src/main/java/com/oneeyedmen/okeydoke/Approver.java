package com.oneeyedmen.okeydoke;

public class Approver extends BaseApprover<Object, String> {

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        this(testName,
                sourceOfApproval,
                Formatters.stringFormatter(),
                Serializers.stringSerializer(),
                Checkers.stringChecker()
        );
    }

    public Approver(String testName,
                    SourceOfApproval sourceOfApproval,
                    Formatter<Object, String> formatter,
                    Serializer<String> serializer,
                    Checker<String> checker) {
        super(testName, sourceOfApproval, formatter, serializer, checker);
    }

    public Transcript transcript() {
        return new StandardTranscript(printStream(), formatter());
    }
}
