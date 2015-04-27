package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class ApprovalsRuleTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test(expected = ComparisonFailure.class)
    public void doesnt_match_where_no_approved_result() {
        approver.assertApproved("banana");
    }

    @Test public void matches_when_approved_result_matches() {
        approver.assertApproved("banana");
    }

    @Test(expected = ComparisonFailure.class)
    public void doesnt_match_when_approved_result_doesnt_match() {
        approver.assertApproved("kumquat");
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
