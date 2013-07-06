package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.rococoa.okeydoke.internal.IO.closeQuietly;

public class Approver {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter formatter = Formatters.stringFormatter();
    private final Formatter<byte[]> binaryFormatter = Formatters.binaryFormatter();

    private OutputStream outputForActual;

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
    }

    public PrintStream printStream() throws IOException {
        return new PrintStream(outputForActual());
    }

    public void assertApproved(Object actual) throws IOException {
        assertApproved(actual, testName);
    }

    public void assertApproved(Object actual, String testname) throws IOException {
        assertApproved(actual, testname, formatter);
    }

    public void approve(Object approved) throws IOException {
        approve(approved, testName);
    }

    public void approve(Object approved, String testname) throws IOException {
        writeAndClose(approved, formatter, sourceOfApproval.outputForApproved(testname));
    }

    public void assertBinaryApproved(byte[] actual) throws IOException {
        assertBinaryApproved(actual, testName);
    }

    public void assertBinaryApproved(byte[] actual, String testname) throws IOException {
        assertApproved(actual, testname, binaryFormatter);
    }

    public void approveBinary(byte[] approved) throws IOException {
        approveBinary(approved, testName);
    }

    public void approveBinary(byte[] approved, String testname) throws IOException {
        writeAndClose(approved, binaryFormatter, sourceOfApproval.outputForApproved(testname));
    }

    public void assertApproved(Object actual, String testname, Formatter aFormatter) throws IOException {

        writeActual(actual, testname, aFormatter);
        resetOutputForActual();

        InputStream approvedInputOrNull = sourceOfApproval.inputOrNullForApproved(testname);
        InputStream actualOutput = sourceOfApproval.inputOrNullForActual(testname);

        if (approvedInputOrNull == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                aFormatter.assertEquals(aFormatter.readFrom(approvedInputOrNull), aFormatter.readFrom(actualOutput));
                return;
            } catch (AssertionError e) {
                reportFailure(testname);
                throw e;
            }
        }
    }

    private void resetOutputForActual() {
        outputForActual = null;
    }

    private void writeActual(Object actual, String testname, Formatter aFormatter) throws IOException {
        if (outputForActual != null && !this.testName.equals(testname))
            throw new IllegalStateException("Once you've started using my PrintStream, you can only approve with the original test name");

        OutputStream localOutputForActual = outputForActual != null ? outputForActual :
                sourceOfApproval.outputForActual(testname);
        writeAndClose(aFormatter.formatted(actual), aFormatter, localOutputForActual);
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

    private OutputStream outputForActual() throws IOException {
        if (outputForActual == null) {
            outputForActual = sourceOfApproval.outputForActual(testName);
        }
        return outputForActual;
    }

}
