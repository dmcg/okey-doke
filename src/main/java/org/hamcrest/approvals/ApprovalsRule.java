package org.hamcrest.approvals;

import org.hamcrest.approvals.internal.IO;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ApprovalsRule extends TestRememberer {

    public ApprovalsRule(String srcRoot) {
        super(new File(srcRoot));
    }

    public void assertApproved(String actual) {
        assertApproved(actual, testName());
    }

    public void assertApproved(String actual, String testname) {
        writeActual(actual, testname);
        String approved = readApproved(testname);
        if (approved == null) {
            throw new AssertionError("No approved thing was found.\n" + toApproveText(testname));
        } else {
            try {
                assertEquals(approved, actual);
                return;
            } catch (AssertionError e) {
                System.err.println(toApproveText(testname));
                throw e;
            }
        }
    }

    public void approve(Object approved) throws IOException {
        writeApproved(approved, testName());
    }

    public File approvedFile() {
        return approvedFileFor(testName());
    }

    private void writeApproved(Object approved, String testname) throws IOException {
        byte[] bytes = approved.toString().getBytes();
        IO.writeBytes(approvedFileFor(testname), bytes);
    }

    String readApproved(String testname) {
        File approvalFile = approvedFileFor(testname);
        return !(approvalFile.exists() && approvalFile.isFile()) ?
                null : new String(IO.readBytes(approvalFile));
    }

    void writeActual(Object actual, String testname) {
        try {
            IO.writeBytes(actualFileFor(testname), String.valueOf(actual).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File approvedFileFor(String testname) {
        return fileFor(testname, ".approved");
    }

    public File actualFile() {
        return actualFileFor(testName());
    }

    private File actualFileFor(String testname) {
        return fileFor(testname, ".actual");
    }

    private File fileFor(String testname, String suffix) {
        return new File(dirForPackage(sourceRoot, testClass), testname + suffix);
    }

    String toApproveText(String testname) {
        return String.format("\nTo approve...\ncp %s %s", actualFileFor(testname), approvedFileFor(testname));
    }


}
