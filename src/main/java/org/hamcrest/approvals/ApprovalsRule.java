package org.hamcrest.approvals;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;
import java.io.IOException;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class ApprovalsRule extends TestWatcher {

    protected final File sourceRoot;
    protected Approver approver;

    public ApprovalsRule(String srcRoot) {
        sourceRoot = new File(srcRoot);
    }

    @Override
    protected void starting(Description description) {
        approver = new Approver(
                Naming.testNameFor(description),
                new FileSystemSourceOfApproval(sourceRoot, description.getTestClass().getPackage()));
    }

    public void assertApproved(String actual) {
        _approver().assertApproved(actual);
    }

    public void assertApproved(String actual, String testname) {
        _approver().assertApproved(actual, testname);
    }

    public void approve(Object approved) throws IOException {
        _approver().approve(approved);
    }

    private Approver _approver() {
        checkRuleState();
        return approver;
    }

    protected void checkRuleState() {
        if (approver == null)
            throw new IllegalStateException("Somethings's wrong - check your " +
                    getClass().getSimpleName() + " is an @Rule field");
    }
}
