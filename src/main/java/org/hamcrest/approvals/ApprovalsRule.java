package org.hamcrest.approvals;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;
import java.io.IOException;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public abstract class ApprovalsRule extends TestWatcher {

    protected Approver approver;

    public static ApprovalsRule fileSystemRule(final String sourceRoot) {
        return new ApprovalsRule() {
            @Override
            protected FileSystemSourceOfApproval createSourceOfApproval(Class<?> testClass) {
                return new FileSystemSourceOfApproval(new File(sourceRoot), testClass.getPackage());
            }
        };
    }

    @Override
    protected void starting(Description description) {
        approver = createApprover(Naming.testNameFor(description), description.getTestClass());
    }

    protected Approver createApprover(String testName, Class<?> testClass) {
        return new Approver(testName, createSourceOfApproval(testClass));
    }

    protected abstract SourceOfApproval createSourceOfApproval(Class<?> testClass);

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
