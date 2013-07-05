package org.rococoa.okeydoke;

import java.io.IOException;
import java.util.Arrays;

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
        CompareResult approval = sourceOfApproval.writeAndCompare(testname, stringFor(actual).getBytes());

        if (approval.errorOrNull != null) {
            // sourceOfApproval has done the comparison for us
            reportFailure(testname);
            throw approval.errorOrNull;
        } else if (approval.approvedOrNull == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                assertEquals(new String(approval.approvedOrNull), stringFor(actual));
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

    private String stringFor(Object actual) {
        if (actual.getClass().isArray())
            return stringFor((Object[]) actual);
        if (actual instanceof Iterable)
            return stringFor((Iterable) actual);
        return String.valueOf(actual);
    }

    private String stringFor(Iterable iterable) {
        StringBuilder result = new StringBuilder("[");
        for (Object o : iterable) {
            result.append("\"").append(stringFor(o)).append("\",");
        }
        result.deleteCharAt(result.length() - 1).append("]");
        return result.toString();
    }

    private String stringFor(Object[] iterable) {
        return stringFor(Arrays.asList(iterable));
    }

    public void approve(Object approved) throws IOException {
        approve(approved, testName);
    }

    public void approve(Object approved, String testname) throws IOException {
        sourceOfApproval.writeApproved(testname, stringFor(approved).getBytes());
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
