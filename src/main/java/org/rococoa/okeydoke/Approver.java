package org.rococoa.okeydoke;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.rococoa.okeydoke.SourceOfApproval.CompareResult;

public class Approver {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
    }

    public void assertApproved(Object actual) {
        assertApproved(actual, testName);
    }

    public void assertApproved(Object actual, String testname) {
        byte[] actualAsBytes = representationOf(actual);

        CompareResult approval = sourceOfApproval.writeAndCompare(testname, actualAsBytes);

        if (approval.errorOrNull != null) {
            // sourceOfApproval has done the comparison for us
            reportFailure(testname);
            throw approval.errorOrNull;
        } else if (approval.approvedOrNull == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                if (actual instanceof String) {
                    // nasty hack for now
                    assertEquals(new String(approval.approvedOrNull), actual);
                } else {
                    assertArrayEquals(approval.approvedOrNull, actualAsBytes);
                }
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

    private byte[] representationOf(Object actual) {
        if (actual instanceof byte[])
            return (byte[]) actual;
        return String.valueOf(actual).getBytes();
    }

    public void approve(Object approved) throws IOException {
        approve(approved, testName);
    }

    public void approve(Object approved, String testname) throws IOException {
        sourceOfApproval.writeApproved(testname, representationOf(approved));
    }

    public void assertBinaryApproved(byte[] actualAsBytes) {
        assertBinaryApproved(actualAsBytes, testName);
    }

    public void assertBinaryApproved(byte[] actualAsBytes, String testname) {
        CompareResult approval = sourceOfApproval.writeAndCompare(testname, actualAsBytes);

        if (approval.errorOrNull != null) {
            // sourceOfApproval has done the comparison for us
            reportFailure(testname);
            throw approval.errorOrNull;
        } else if (approval.approvedOrNull == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                assertArrayEquals(approval.approvedOrNull, actualAsBytes);
                return;
            } catch (AssertionError e) {
                reportFailure(testname);
                throw e;
            }
        }
    }

    public void approveBinary(byte[] approved) throws IOException {
        approveBinary(approved, testName);
    }

    public void approveBinary(byte[] approved, String testname) throws IOException {
        sourceOfApproval.writeApproved(testname, approved);
    }

}
