package com.oneeyedmen.okeydoke.junit;

import com.oneeyedmen.okeydoke.ApproverFactory;
import com.oneeyedmen.okeydoke.BaseApprover;
import com.oneeyedmen.okeydoke.Formatter;
import com.oneeyedmen.okeydoke.internal.Naming;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class BaseApprovalsRule<T, C, A extends BaseApprover<T,C>> extends TestWatcher {

    private A approver;
    private final ApproverFactory<A> factory;

    public BaseApprovalsRule(ApproverFactory<A> factory) {
        this.factory = factory;
    }

    public PrintStream printStream() {
        return approver().printStream();
    }

    public OutputStream outputStream() throws IOException {
        return approver.outputStream();
    }

    public void writeFormatted(T o) {
        approver().writeFormatted(o);
    }

    public void assertApproved(T actual) {
        approver().assertApproved(actual);
    }

    public <T2 extends T> void assertApproved(T2 actual, Formatter<T2, C> formatter) {
        approver().assertApproved(actual, formatter);
    }

    public void assertSatisfied() {
        if (approver().satisfactionChecked())
            throw new IllegalStateException("I've got too much satisfaction");
        approver().assertSatisfied();
    }

    public void makeApproved(T approved) throws IOException {
        approver().makeApproved(approved);
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
        return factory.createApprover(testName, testClass);
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
