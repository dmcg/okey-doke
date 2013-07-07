package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.rococoa.okeydoke.internal.IO.closeQuietly;

public class Approver {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter formatter;

    private final OutputStream outputForActual;

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        this(testName,  sourceOfApproval, Formatters.stringFormatter());
    }

    public Approver(String testName, SourceOfApproval sourceOfApproval, Formatter formatter) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
        this.formatter = formatter;
        try {
            outputForActual = sourceOfApproval.outputForActual(testName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public PrintStream printStream() throws IOException {
        return new PrintStream(outputForActual);
    }

    public void assertApproved(Object actual) throws IOException {
        assertApproved(actual, formatter);
    }

    public void approve(Object approved) throws IOException {
        writeAndClose(approved, formatter, sourceOfApproval.outputForApproved(testName));
    }

    public void assertApproved(Object actual, Formatter aFormatter) throws IOException {

        writeActual(actual, aFormatter);

        InputStream inputForApprovedOrNull = sourceOfApproval.inputOrNullForApproved(testName);
        InputStream inputForActualOrNull = sourceOfApproval.inputOrNullForActual(testName);

        if (inputForApprovedOrNull == null)
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testName));
        if (inputForActualOrNull == null)
            throw new AssertionError("This is embarrassing - I've lost the actual I just wrote for " + testName);

        try {
            aFormatter.assertEquals(aFormatter.readFrom(inputForApprovedOrNull), aFormatter.readFrom(inputForActualOrNull));
            return;
        } catch (AssertionError e) {
            reportFailure(testName);
            throw e;
        }
    }

    private void writeActual(Object actual, Formatter aFormatter) throws IOException {
        writeAndClose(aFormatter.formatted(actual), aFormatter, outputForActual);
    }

    private void writeAndClose(Object formattedActual, Formatter aFormatter, OutputStream output) throws IOException {
        try {
            aFormatter.writeTo(formattedActual, output);
        } finally {
            closeQuietly(output);
        }
    }

    private void reportFailure(String testname) {
        System.err.println(sourceOfApproval.toApproveText(testname));
    }

}
