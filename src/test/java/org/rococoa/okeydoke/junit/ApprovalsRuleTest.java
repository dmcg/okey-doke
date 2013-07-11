package org.rococoa.okeydoke.junit;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class ApprovalsRuleTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("target/approvals");

    @Test(expected = AssertionError.class)
    public void doesnt_match_where_no_approved_result() throws IOException {
        approver.assertApproved("banana");
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.approve("banana");
        approver.assertApproved("banana");
    }

    @Test(expected = AssertionError.class)
    public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.approve("banana");
        approver.assertApproved("kumquat");
    }

    @Ignore("Unignore to see no approval in IDE")
    @Test public void see_how_my_IDE_reports_no_approval() throws IOException {
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    @Ignore("Unignore to see failure report in IDE")
    @Test public void see_how_my_IDE_reports_diffs() throws IOException {
        approver.approve("Deliberate failure - Jackdaws love my big sphinx of quartz");
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }
}
