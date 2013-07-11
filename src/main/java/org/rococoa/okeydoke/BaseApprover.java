package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.ComparingOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.rococoa.okeydoke.internal.IO.closeQuietly;

public class BaseApprover<T, C> {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter<T, C> formatter;

    private OutputStream osForActual;
    private boolean done;

    protected BaseApprover(String testName, SourceOfApproval sourceOfApproval, Formatter<T, C> formatter) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
        this.formatter = formatter;
    }

    public PrintStream printStream() throws IOException {
        return new PrintStream(osForActual());
    }

    public void writeFormatted(T object) throws IOException {
        writeFormatted(object, formatter);
    }

    public void writeFormatted(T object, Formatter<T, C> aFormatter) throws IOException {
        aFormatter.writeTo(aFormatter.formatted(object), osForActual());
    }

    public void assertApproved(T actual) throws IOException {
        assertApproved(actual, formatter);
    }

    public void assertApproved(T actual, Formatter<T, C> aFormatter) throws IOException {
        writeFormatted(actual, aFormatter);
        assertSatisfied();
    }

    public void assertSatisfied() throws IOException {
        try {
            osForActual().close();
            if (osForActual() instanceof ComparingOutputStream) {
                long mismatchPosition = ((ComparingOutputStream) osForActual()).firstMismatchPosition();
                if (mismatchPosition != -1)
                    throw new AssertionError("Streams differed at " + mismatchPosition);
            }

            InputStream inputForApprovedOrNull = sourceOfApproval.inputOrNullForApproved(testName);
            InputStream inputForActualOrNull = sourceOfApproval.inputOrNullForActual(testName);

            if (inputForApprovedOrNull == null)
                throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testName));
            if (inputForActualOrNull == null)
                throw new AssertionError("This is embarrassing - I've lost the actual I just wrote for " + testName);

            try {
                formatter.assertEquals(formatter.readFrom(inputForApprovedOrNull), formatter.readFrom(inputForActualOrNull));
            } catch (AssertionError e) {
                sourceOfApproval.reportFailure(testName, e);
                throw e;
            }
        } finally {
            osForActual = null;
            done = true;
        }
    }

    public void approve(T approved) throws IOException {
        OutputStream output = sourceOfApproval.outputForApproved(testName);
        try {
            formatter.writeTo(formatter.formatted(approved), output);
        } finally {
            closeQuietly(output);
        }
    }

    public boolean satisfactionChecked() {
        return done;
    }

    public Formatter<T, C> formatter() {
        return formatter;
    }

    protected OutputStream osForActual() throws IOException {
        if (osForActual == null)
            osForActual = sourceOfApproval.outputForActual(testName);
        return osForActual;
    }
}
