package org.hamcrest.approvals;

import org.hamcrest.Matcher;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.io.IOException;

public class ApprovalsRule extends TestWatchman {

    private String testName;

    @Override
    public void starting(FrameworkMethod method) {
        testName = method.getName();
    }

    public String testName() {
        return testName;
    }

    public void forgetApproval() {
        Approvals.forgetApproval(testName());
    }

    public Matcher<? super String> isApproved() {
        return Approvals.isAsApproved(testName());
    }

    public void approve(String approved) throws IOException {
        Approvals.approve(approved, testName());
    }
}
