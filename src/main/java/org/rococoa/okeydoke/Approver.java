package org.rococoa.okeydoke;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Approver {

    private final String testName;
    private final SourceOfApproval sourceOfApproval;

    public Approver(String testName, SourceOfApproval sourceOfApproval) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
    }

    public Approver(String testName, File sourceRoot, Class<?> test) {
        this(testName, new FileSystemSourceOfApproval(sourceRoot, test.getPackage()));
    }

    public void assertApproved(Object actual) {
        assertApproved(actual, testName);
    }

    public void assertApproved(Object actual, String testname) {
        byte[] actualAsBytes = representationOf(actual);
        sourceOfApproval.writeActual(testname, actualAsBytes);
        byte[] approved = sourceOfApproval.readApproved(testname);
        if (approved == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                if (actual instanceof String) {
                    // nasty hack for now
                    assertEquals(new String(approved), (String)actual);
                } else {
                    assertArrayEquals(approved, actualAsBytes);
                }
                return;
            } catch (AssertionError e) {
                System.err.println(sourceOfApproval.toApproveText(testname));
                throw e;
            }
        }
    }

    private byte[] representationOf(Object actual) {
        return String.valueOf(actual).getBytes();
    }

    public void approve(Object approved) throws IOException {
        sourceOfApproval.writeApproved(testName, representationOf(approved));
    }

}
