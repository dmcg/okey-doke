package org.rococoa.okeydoke;

import java.io.IOException;

import static org.rococoa.okeydoke.SourceOfApproval.CompareResult;

public class Approver {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;
    private final Formatter formatter = Formatters.stringFormatter();
    private final Formatter<byte[]> binaryFormatter = Formatters.binaryFormatter();

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
    }

    public void assertApproved(Object actual) {
        assertApproved(actual, testName);
    }

    public void assertApproved(Object actual, String testname) {
        assertApproved(actual, testname, formatter);
    }

    public void approve(Object approved) throws IOException {
        approve(approved, testName);
    }

    public void approve(Object approved, String testname) throws IOException {
        sourceOfApproval.writeApproved(testname, formatter.bytesFor(approved));
    }

    public void assertBinaryApproved(byte[] actual) {
        assertBinaryApproved(actual, testName);
    }

    public void assertBinaryApproved(byte[] actual, String testname) {
        assertApproved(actual, testname, binaryFormatter);
    }

    public void approveBinary(byte[] approved) throws IOException {
        approveBinary(approved, testName);
    }

    public void approveBinary(byte[] approved, String testname) throws IOException {
        sourceOfApproval.writeApproved(testname, approved);
    }

    public void assertApproved(Object actual, String testname, Formatter aFormatter) {
        CompareResult approval = sourceOfApproval.writeAndCompare(
                testname,
                aFormatter.bytesFor(aFormatter.formatted(actual)));

        if (approval.errorOrNull != null) {
            // sourceOfApproval has done the comparison for us
            reportFailure(testname);
            throw approval.errorOrNull;
        } else if (approval.approvedOrNull == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                aFormatter.assertEquals(aFormatter.objectFor(approval.approvedOrNull), aFormatter.formatted(actual));
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
