package org.hamcrest.approvals;

import java.io.File;
import java.io.IOException;

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

    public void assertApproved(String actual) {
        assertApproved(actual, testName);
    }

    public void assertApproved(String actual, String testname) {
        sourceOfApproval.writeActual(testname, String.valueOf(actual).getBytes());
        String approved = sourceOfApproval.readApproved(testname);
        if (approved == null) {
            throw new AssertionError("No approved thing was found.\n" + sourceOfApproval.toApproveText(testname));
        } else {
            try {
                assertEquals(approved, actual);
                return;
            } catch (AssertionError e) {
                System.err.println(sourceOfApproval.toApproveText(testname));
                throw e;
            }
        }
    }

    public void approve(Object approved) throws IOException {
        sourceOfApproval.writeApproved(testName, String.valueOf(approved).getBytes());
    }

}
