package org.rococoa.okeydoke.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.BaseApprover;
import org.rococoa.okeydoke.BaseApproverFactory;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.internal.Naming;

import java.io.PrintStream;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class BaseApprovalsRule<T, C, A extends BaseApprover<T,C,?>> extends TestWatcher {

    private A approver;
    private final BaseApproverFactory<T, A> factory;

    public BaseApprovalsRule(BaseApproverFactory<T,A> factory) {
        this.factory = factory;
    }

    public PrintStream printStream() {
        return approver().printStream();
    }

    public void writeFormatted(T o) {
        approver().writeFormatted(o);
    }

    public void assertApproved(T actual) {
        approver().assertApproved(actual);
    }

    public void assertApproved(T actual, Formatter<T, C> formatter) {
        approver().assertApproved(actual, formatter);
    }

    public void assertSatisfied() {
        if (approver().satisfactionChecked())
            throw new IllegalStateException("I've got too much satisfaction");
        approver().assertSatisfied();
    }

    public void approve(T approved) {
        approver().approve(approved);
    }

    @Override
    public void starting(Description description) {
        approver = createApprover(Naming.testNameFor(description), description.getTestClass());
    }

    @Override
    public void succeeded(Description description) {
        if (approver().satisfactionChecked())
            return;
        assertSatisfied();
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
