package org.rococoa.okeydoke.testutils;

import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.junit.ApprovalsRule;

public class PrecannedApprovalsRule {

    public static ApprovalsRule with(final Approver delegate) {
        return new ApprovalsRule(new ApproverFactory() {
            @Override
            public Approver create(String testName, Class<?> testClass) {
                return delegate;
            }
        });
    }
}
