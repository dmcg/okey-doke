package com.oneeyedmen.okeydoke.junit;

import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApprovalsRuleTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("target/approvals");

    @Test
    public void doesnt_match_where_no_approved_result() {
        try {
            approver.assertApproved("banana");
            fail();
        } catch (ComparisonFailure expected) {
            assertEquals("banana", expected.getActual());
            assertEquals("", expected.getExpected());
        }
    }

    @Test public void matches_when_approved_result_matches() {
        approver.makeApproved("banana");
        approver.assertApproved("banana");
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() {
        approver.makeApproved("banana");
        try {
            approver.assertApproved("kumquat");
            fail();
        } catch (ComparisonFailure expected) {
            assertEquals("kumquat", expected.getActual());
            assertEquals("banana", expected.getExpected());
        }
    }

    @Ignore("Unignore to see no approval in IDE")
    @Test public void see_how_my_IDE_reports_no_approval() {
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    @Ignore("Unignore to see failure report in IDE")
    @Test public void see_how_my_IDE_reports_diffs() {
        approver.makeApproved("Deliberate failure - Jackdaws love my big sphinx of quartz");
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }
}
