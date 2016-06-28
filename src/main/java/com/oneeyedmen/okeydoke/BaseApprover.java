package com.oneeyedmen.okeydoke;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static com.oneeyedmen.okeydoke.internal.IO.closeQuietly;

public class BaseApprover<ApprovedT, ComparedT> {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter<ApprovedT, ComparedT> formatter;
    private final Serializer<ComparedT> serializer;
    private final Checker<ComparedT> checker;

    private Resource actual;
    private boolean done;

    protected BaseApprover(String testName, SourceOfApproval sourceOfApproval,
                           Formatter<ApprovedT, ComparedT> formatter,
                           Serializer<ComparedT> serializer,
                           Checker<ComparedT> checker) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
        this.formatter = formatter;
        this.serializer = serializer;
        this.checker = checker;
    }

    public PrintStream printStream() {
        try {
            return new PrintStream(getActual().outputStream());
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public OutputStream outputStream() throws IOException {
        return getActual().outputStream();
    }

    public void writeFormatted(ApprovedT object) {
        writeFormatted(object, formatter);
    }

    public <AT extends ApprovedT> void writeFormatted(AT object, Formatter<AT, ComparedT> aFormatter) {
        try {
            serializer.writeTo(aFormatter.formatted(object), getActual().outputStream());
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public void assertApproved(ApprovedT actual) {
        assertApproved(actual, formatter);
    }

    public <AT extends ApprovedT> void assertApproved(AT actual, Formatter<AT, ComparedT> aFormatter) {
        writeFormatted(actual, aFormatter);
        assertSatisfied();
    }

    @SuppressWarnings("FeatureEnvy" /* keeps sourceOfApproval simple */)
    public void assertSatisfied() {
        try {
            OutputStream outputStream = getActual().outputStream();
            outputStream.close();
            sourceOfApproval.checkActualAgainstApproved(outputStream, testName(), serializer, checker);
            getActual().remove();
        } catch (AssertionError e) {
            sourceOfApproval.reportFailure(testName(), e);
            throw e;
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            actual = null;
            done = true;
        }
    }

    public void makeApproved(ApprovedT approved) throws IOException {
        OutputStream output = sourceOfApproval.approvedFor(testName()).outputStream();
        try {
            serializer.writeTo(formatter.formatted(approved), output);
        } finally {
            closeQuietly(output);
        }
    }

    public boolean satisfactionChecked() {
        return done;
    }

    public Formatter<ApprovedT, ComparedT> formatter() {
        return formatter;
    }

    private Resource getActual() throws IOException {
        if (actual == null)
            actual = sourceOfApproval.actualFor(testName());
        return actual;
    }

    protected String testName() {
        return testName;
    }

    public void removeApproved() throws IOException {
        sourceOfApproval.approvedFor(testName()).remove();
    }

}
