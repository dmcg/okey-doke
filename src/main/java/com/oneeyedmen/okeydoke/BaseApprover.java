package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.internal.IO;
import com.oneeyedmen.okeydoke.sources.Snitch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static com.oneeyedmen.okeydoke.internal.IO.closeQuietly;

public class BaseApprover<ApprovedT, ComparedT, StorageT> {

    private final String testName;
    private final SourceOfApproval<StorageT> sourceOfApproval;
    private final Formatter<ApprovedT, ComparedT> formatter;
    private final Serializer<ComparedT> serializer;
    private final Checker<ComparedT> checker;

    private OutputStream osForActual;
    private boolean done;

    protected BaseApprover(String testName, SourceOfApproval<StorageT> sourceOfApproval,
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
                    checkByReading();
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

    private void checkByReading() throws IOException {
        InputStream approved = inputForApproved();
        InputStream actual = inputForActual();
        try {
            checker.assertEquals(serializer.readFrom(approved), serializer.readFrom(actual));
        } finally {
            IO.closeQuietly(actual);
            IO.closeQuietly(approved);
        }
    }

    private InputStream inputForActual() throws IOException {
        InputStream result = sourceOfApproval.inputOrNullForActual(testName());
        if (result != null) {
            return result;
        }
        throw new AssertionError("This is embarrassing - I've lost the 'actual' I just wrote for " + testName());
            // Can happen if we have opened file early and then delete the directory
    }

    private InputStream inputForApproved() throws IOException {
        InputStream existing = sourceOfApproval.inputOrNullForApproved(testName());
        if (existing != null)
            return existing;
        writeToApproved(serializer.emptyThing());
        return sourceOfApproval.inputOrNullForApproved(testName());
    }

    public void makeApproved(ApprovedT approved) {
        writeToApproved(formatter.formatted(approved));
    }

    private void writeToApproved(ComparedT formatted) {
        try {
            OutputStream output = sourceOfApproval.outputForApproved(testName());
            try {
                serializer.writeTo(formatted, output);
            } finally {
                closeQuietly(output);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
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

    public ComparedT readActual() {
        try {
            InputStream inputForActualOrNull = sourceOfApproval.inputOrNullForActual(testName());
            try {
                if (inputForActualOrNull == null)
                    throw new AssertionError("This is embarrassing - I've lost the 'actual' I just wrote for " + testName());
                return serializer.readFrom(inputForActualOrNull);
            } finally {
                IO.closeQuietly(inputForActualOrNull);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    protected String testName() {
        return testName;
    }
}
