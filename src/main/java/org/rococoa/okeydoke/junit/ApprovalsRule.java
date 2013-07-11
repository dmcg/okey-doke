package org.rococoa.okeydoke.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.FileSystemSourceOfApproval;
import org.rococoa.okeydoke.SourceOfApproval;
import org.rococoa.okeydoke.Transcript;
import org.rococoa.okeydoke.internal.Naming;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

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

    public static ApprovalsRule fileSystemRule(final String sourceRoot, final String actualDir) {
        return new ApprovalsRule() {
            @Override
            protected FileSystemSourceOfApproval createSourceOfApproval(Class<?> testClass) {
                return new FileSystemSourceOfApproval(new File(sourceRoot), testClass.getPackage(), new File(actualDir));
            }
        };
    }

    public PrintStream printStream() throws IOException {
        return approver().printStream();
    }

    public void writeFormatted(Object o) throws IOException {
        approver().writeFormatted(o);
    }

    public void assertApproved(Object actual) throws IOException {
        approver().assertApproved(actual);
    }

    public void assertSatisfied() throws IOException {
        if (approver().satisfactionChecked())
            throw new IllegalStateException("I've got too much satisfaction");
        approver().assertSatisfied();
    }

    public void approve(Object approved) throws IOException {
        approver().approve(approved);
    }

    @Override
    protected void starting(Description description) {
        approver = createApprover(Naming.testNameFor(description), description.getTestClass());
    }

    @Override
    protected void succeeded(Description description) {
        if (approver().satisfactionChecked())
            return;
        try {
            assertSatisfied();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Approver createApprover(String testName, Class<?> testClass) {
        return new Approver(testName, createSourceOfApproval(testClass));
    }

    protected abstract SourceOfApproval createSourceOfApproval(Class<?> testClass);

    public Approver approver() {
        checkRuleState();
        return approver;
    }

    protected void checkRuleState() {
        if (approver == null)
            throw new IllegalStateException("Somethings's wrong - check your " +
                    getClass().getSimpleName() + " is an @Rule field");
    }

    public Transcript transcript() throws IOException {
        return approver().transcript();
    }
}
