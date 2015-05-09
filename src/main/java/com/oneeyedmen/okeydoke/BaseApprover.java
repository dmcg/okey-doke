package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.sources.Snitch;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class BaseApprover<ApprovedT, ComparedT> {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter<ApprovedT, ComparedT> formatter;
    private final Serializer<ComparedT> serializer;
    private final Checker<ComparedT> checker;

    private OutputStream osForActual;
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
            return new PrintStream(osForActual());
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public OutputStream outputStream() throws IOException {
        return osForActual();
    }

    public void writeFormatted(ApprovedT object) {
        writeFormatted(object, formatter);
    }

    public <AT extends ApprovedT> void writeFormatted(AT object, Formatter<AT, ComparedT> aFormatter) {
        try {
            serializer.writeTo(aFormatter.formatted(object), osForActual());
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

    public void assertSatisfied() {
        try {
            osForActual().close();
            try {
                if (osForActual() instanceof Snitch) {
                    ((Snitch) osForActual()).tellIf();
                } else {
                    sourceOfApproval.checkActualAgainstApproved(testName(), serializer, checker);
                }
                sourceOfApproval.removeActual(testName());
            } catch (AssertionError e) {
                sourceOfApproval.reportFailure(testName(), e);
                throw e;
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            osForActual = null;
            done = true;
        }
    }


    public void makeApproved(ApprovedT approved) throws IOException {
        writeToApproved(formatter.formatted(approved));
    }

    private void writeToApproved(ComparedT formatted) throws IOException {
        sourceOfApproval.writeToApproved(testName(), formatted, serializer);
    }

    public boolean satisfactionChecked() {
        return done;
    }

    public Formatter<ApprovedT, ComparedT> formatter() {
        return formatter;
    }

    protected OutputStream osForActual() throws IOException {
        if (osForActual == null)
            osForActual = sourceOfApproval.outputForActual(testName());
        return osForActual;
    }

    public ComparedT readActual() throws IOException {
        return sourceOfApproval.readActual(testName(), serializer);
    }

    protected String testName() {
        return testName;
    }
}
