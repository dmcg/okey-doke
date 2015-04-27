package com.oneeyedmen.okeydoke.testutils;

import com.oneeyedmen.okeydoke.Approver;
import com.oneeyedmen.okeydoke.ApproverFactory;
import com.oneeyedmen.okeydoke.junit.ApprovalsRule;

public class PrecannedApprovalsRule {

    public static ApprovalsRule with(final Approver delegate) {
        return new ApprovalsRule(new ApproverFactory<Approver>() {
            @Override
            public Approver createApprover(String testName, Class<?> testClass) {
                return delegate;
            }
        });
    }
}
