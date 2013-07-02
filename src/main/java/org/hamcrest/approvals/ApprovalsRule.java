package org.hamcrest.approvals;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;
import java.io.IOException;


public class ApprovalsRule extends TestWatcher {

    protected final File sourceRoot;
    private Approver approver;

    public ApprovalsRule(String srcRoot) {
        sourceRoot = new File(srcRoot);
    }

    @Override
    protected void starting(Description description) {
        approver = new Approver(Naming.dirForPackage(sourceRoot, description.getTestClass()),
                Naming.testNameFor(description));
    }

    public void assertApproved(String actual) {
        approver.assertApproved(actual);
    }

    public void assertApproved(String actual, String testname) {
        approver.assertApproved(actual, testname);
    }

    public void approve(Object approved) throws IOException {
        approver.approve(approved);
    }

    public File approvedFile() {
        return approver.approvedFile();
    }

    public File actualFile() {
        return approver.actualFile();
    }

}
