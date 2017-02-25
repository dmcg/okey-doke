package com.oneeyedmen.okeydoke.junit;

import com.oneeyedmen.okeydoke.ApproverFactory;
import com.oneeyedmen.okeydoke.BaseApprover;
import com.oneeyedmen.okeydoke.Formatter;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class BaseApprovalsRule<ApprovedT, ComparedT, ApproverT extends BaseApprover<ApprovedT, ComparedT>> extends TestWatcher {

    private final ApproverFactory<ApproverT> factory;
    private final TestNamer testNamer;
    private ApproverT approver; // can only be bound once the test starts

    public BaseApprovalsRule(ApproverFactory<ApproverT> factory) {
        this(factory, new StandardTestNamer());
    }

    public BaseApprovalsRule(ApproverFactory<ApproverT> factory, TestNamer testNamer) {
        this.factory = factory;
        this.testNamer = testNamer;
    }

    public PrintStream printStream() {
        return approver().printStream();
    }

    public OutputStream outputStream() throws IOException {
        return approver.outputStream();
    }

    public void writeFormatted(ApprovedT o) {
        approver().writeFormatted(o);
    }

    public void assertApproved(ApprovedT actual) {
        approver().assertApproved(actual);
    }

    public <T2 extends ApprovedT> void assertApproved(T2 actual, Formatter<T2, ComparedT> formatter) {
        approver().assertApproved(actual, formatter);
    }

    public void assertSatisfied() {
        if (approver().satisfactionChecked())
            throw new IllegalStateException("I've got too much satisfaction");
        approver().assertSatisfied();
    }

    public void makeApproved(ApprovedT approved) throws IOException {
        approver().makeApproved(approved);
    }

    @Override
    public void starting(Description description) {
        approver = createApprover(testNamer.nameFor(description), description.getTestClass());
    }

    @Override
    public void succeeded(Description description) {
        if (approver().satisfactionChecked())
            return;
        assertSatisfied();
    }

    protected ApproverT createApprover(String testName, Class<?> testClass) {
        return factory.createApprover(testName, testClass);
    }

    public ApproverT approver() {
        checkRuleState();
        return approver;
    }

    protected void checkRuleState() {
        if (approver == null)
            throw new IllegalStateException("Something is wrong - check your " +
                    getClass().getSimpleName() + " is an @Rule field");
    }

    public void removeApproved() throws IOException {
        approver().removeApproved();
    }
}
