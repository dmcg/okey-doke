package org.hamcrest.approvals;

import org.hamcrest.approvals.rules.CleanDirectoryRule;
import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.approvals.rules.CleanDirectoryRule.dirForPackage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApprovalsRuleTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(dirForPackage("target/approvals", this));

    @Rule public final ApprovalsRule approver = new ApprovalsRule("target/approvals");

    @Test public void doesnt_match_where_no_approved_result() {
        try {
            approver.assertApproved("banana");
            fail();
        } catch (AssertionError expected) {}
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.approve("banana");
        approver.assertApproved("banana");
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.approve("banana");
        try {
            approver.assertApproved("kumquat");
            fail();
        } catch (ComparisonFailure expected) {}
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

    @Test public void writes_files_in_package() throws IOException {
        assertEquals(
                new File(dirForPackage("target/approvals", this), "ApprovalsRuleTest.writes_files_in_package.approved"),
                approver.approvedFile());
        assertEquals(
                new File(dirForPackage("target/approvals", this), "ApprovalsRuleTest.writes_files_in_package.actual"),
                approver.actualFile());
    }

}
