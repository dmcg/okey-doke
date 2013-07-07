package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.rococoa.okeydoke.internal.IO.closeQuietly;

public class BaseApprover<T> {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter<T> formatter;

    private OutputStream osForActual;

    protected BaseApprover(String testName, SourceOfApproval sourceOfApproval, Formatter<T> formatter) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
        this.formatter = formatter;
        try {
            osForActual = sourceOfApproval.outputForActual(testName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PrintStream printStream() {
        return new PrintStream(osForActual);
    }

    public void writeFormatted(Object object) throws IOException {
        writeFormatted(object, formatter);
    }

    public void writeFormatted(Object object, Formatter<T> aFormatter) throws IOException {
        aFormatter.writeTo(aFormatter.formatted(object), osForActual);
    }

    public void assertApproved(Object actual) throws IOException {
        assertApproved(actual, formatter);
    }

    public void assertApproved(Object actual, Formatter<T> aFormatter) throws IOException {
        writeFormatted(actual, aFormatter);
        assertSatisfied();
    }

    public void assertSatisfied() throws IOException {
        osForActual.close();
        osForActual = null;

        InputStream inputForApprovedOrNull = sourceOfApproval.inputOrNullForApproved(testName);
        InputStream inputForActualOrNull = sourceOfApproval.inputOrNullForActual(testName);

        if (inputForApprovedOrNull == null)
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testName));
        if (inputForActualOrNull == null)
            throw new AssertionError("This is embarrassing - I've lost the actual I just wrote for " + testName);

        try {
            formatter.assertEquals(formatter.readFrom(inputForApprovedOrNull), formatter.readFrom(inputForActualOrNull));
        } catch (AssertionError e) {
            reportFailure(testName);
            throw e;
        }
    }

    public void approve(Object approved) throws IOException {
        OutputStream output = sourceOfApproval.outputForApproved(testName);
        try {
            formatter.writeTo(formatter.formatted(approved), output);
        } finally {
            closeQuietly(output);
        }
    }

    public boolean satisfactionChecked() {
        return osForActual == null;
    }

    public Formatter<T> formatter() {
        return formatter;
    }

    private void reportFailure(String testname) {
        System.err.println(sourceOfApproval.toApproveText(testname));
    }

}
