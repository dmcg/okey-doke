package org.rococoa.okeydoke.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.BaseApprover;
import org.rococoa.okeydoke.BaseApproverFactory;
import org.rococoa.okeydoke.internal.Naming;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class BaseApprovalsRule<T, A extends BaseApprover<T,?,?>> extends TestWatcher {

    protected A approver;
    private final BaseApproverFactory<T, A> factory;

    public BaseApprovalsRule(BaseApproverFactory<T,A> factory) {
        this.factory = factory;
    }

    public PrintStream printStream() throws IOException {
        return approver().printStream();
    }

    public void writeFormatted(T o) throws IOException {
        approver().writeFormatted(o);
    }

    public void assertApproved(T actual) throws IOException {
        approver().assertApproved(actual);
    }

    public void assertSatisfied() throws IOException {
        if (approver().satisfactionChecked())
            throw new IllegalStateException("I've got too much satisfaction");
        approver().assertSatisfied();
    }

    public void approve(T approved) throws IOException {
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

    protected A createApprover(String testName, Class<?> testClass) {
        return factory.create(testName, testClass);
    }

    public A approver() {
        checkRuleState();
        return approver;
    }

    protected void checkRuleState() {
        if (approver == null)
            throw new IllegalStateException("Something is wrong - check your " +
                    getClass().getSimpleName() + " is an @Rule field");
    }
}
