package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.rococoa.okeydoke.internal.IO.closeQuietly;

public class Approver {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter formatter = Formatters.stringFormatter();
    private final Formatter<byte[]> binaryFormatter = Formatters.binaryFormatter();

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
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
        OutputStream outputStream = sourceOfApproval.approvedOutputFor(testname);
        try {
            formatter.writeTo(approved, outputStream);
        } finally {
            closeQuietly(outputStream);
        }
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
        OutputStream outputStream = sourceOfApproval.approvedOutputFor(testname);
        writeAndClose(approved, outputStream);
    }

    private void writeAndClose(byte[] approved, OutputStream outputStream) throws IOException {
        try {
            outputStream.write(approved);
        } finally {
            closeQuietly(outputStream);
        }
    }

    public void assertApproved(Object actual, String testname, Formatter aFormatter) throws IOException {
        Object formattedActual = aFormatter.formatted(actual);

        OutputStream actualOutput = sourceOfApproval.actualOutputFor(testname);
        try {
            aFormatter.writeTo(formattedActual, actualOutput);
        } finally {
            closeQuietly(actualOutput);
        }

        InputStream approvedInputOrNull = sourceOfApproval.approvedInputOrNullFor(testname);

        if (approvedInputOrNull == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                aFormatter.assertEquals(aFormatter.readFrom(approvedInputOrNull), formattedActual);
                return;
            } catch (AssertionError e) {
                reportFailure(testname);
                throw e;
            }
        }
    }

    private void reportFailure(String testname) {
        System.err.println(sourceOfApproval.toApproveText(testname));
    }
}
