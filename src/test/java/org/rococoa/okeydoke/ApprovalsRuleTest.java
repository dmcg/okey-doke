package org.rococoa.okeydoke;

import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.rococoa.okeydoke.testutils.CleanDirectoryRule.dirForPackage;

public class ApprovalsRuleTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(dirForPackage("target/approvals", this));

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("target/approvals");

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

//    @Test public void writes_files_in_package() throws IOException {
//        assertEquals(
//                new File(dirForPackage("target/approvals", this), "ApprovalsRuleTest.writes_files_in_package.approved"),
//                approver.approvedFile());
//        assertEquals(
//                new File(dirForPackage("target/approvals", this), "ApprovalsRuleTest.writes_files_in_package.actual"),
//                approver.actualFile());
//    }

}
