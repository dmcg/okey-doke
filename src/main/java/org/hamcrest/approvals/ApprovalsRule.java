package org.hamcrest.approvals;

import org.hamcrest.Matcher;
import org.hamcrest.approvals.internal.ForceApprovalMatcher;
import org.hamcrest.approvals.internal.IO;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ApprovalsRule extends TestRememberer {

    public ApprovalsRule(String srcRoot) {
        super(srcRoot);
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

    public <T> Matcher<T> isAsApproved() {
        return isAsApproved(testName());
    }

    public <T> Matcher<T> isAsApproved(String testname) {
        return Matchers.isAsApproved(this, testname);
    }

    public void approve(Object approved) throws IOException {
        writeApproved(approved, testName());
    }

    public File approvedFile() {
        return approvedFileFor(testName());
    }

    public <T> Matcher<T> FORCE_APPROVAL() {
        return FORCE_APPROVAL(testName());
    }

    public <T> Matcher<T> FORCE_APPROVAL(final String testname) {
        return new ForceApprovalMatcher<T>(this, testname);
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

    <T> void writeActual(T actual, String testname) {
        try {
            IO.writeBytes(actualFileFor(testname), actual.toString().getBytes());
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
