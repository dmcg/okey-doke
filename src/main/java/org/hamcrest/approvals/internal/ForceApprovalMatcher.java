package org.hamcrest.approvals.internal;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.approvals.ApprovalsRule;

import java.io.IOException;

public class ForceApprovalMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private final ApprovalsRule approvalsRule;
    private final String testname;

    public ForceApprovalMatcher(ApprovalsRule approvalsRule, String testname) {
        this.approvalsRule = approvalsRule;
        this.testname = testname;
    }

    @Override
    protected boolean matchesSafely(T s, Description description) {
        try {
            approvalsRule.approve(s);
        } catch (IOException e) {
            description.appendText("Couldn't force approval for ").appendValue(testname);
            return false;
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("FORCING APPROVAL OF ").appendValue(testname);
    }
}
