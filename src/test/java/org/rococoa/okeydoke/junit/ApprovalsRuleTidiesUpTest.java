package org.rococoa.okeydoke.junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.Sources;

import java.io.IOException;

import static org.junit.Assert.fail;

public class ApprovalsRuleTidiesUpTest {

    private final Approver delegate = new Approver("testname", Sources.in("target/approvals"));

    private final TestWatcher checkDelegateIsCheckedRule = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            if (!delegate.satisfactionChecked())
                fail("Rule didn't check delegate's satisfaction");
        }
    };

    private final ApprovalsRule approver = new PrecannedApprovalsRule(delegate);

    @Rule public final RuleChain rules = RuleChain.outerRule(checkDelegateIsCheckedRule).around(approver);

    @Test public void rule_will_check_satisfaction() throws IOException {
        approver.approve("banana");

        approver.writeFormatted("banana");
    }

    private class PrecannedApprovalsRule extends ApprovalsRule {
        private final Approver delegate;

        public PrecannedApprovalsRule(Approver delegate) {
            super(null);
            this.delegate = delegate;
        }

        @Override protected Approver createApprover(String testName, Class<?> testClass) {
            return delegate;
        }
    }
}
