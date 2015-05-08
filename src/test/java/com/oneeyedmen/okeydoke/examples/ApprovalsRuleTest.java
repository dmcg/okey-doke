package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class ApprovalsRuleTest {

    //README_TEXT

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test(expected = ComparisonFailure.class)
    public void doesnt_match_where_no_approved_result() {
        assertFileNotExists("ApprovalsRuleTest.doesnt_match_where_no_approved_result.approved");
        approver.assertApproved("banana");
    }

    @Test public void matches_when_approved_result_matches() {
        assertFileExists("ApprovalsRuleTest.matches_when_approved_result_matches.approved");
        approver.assertApproved("banana");
    }

    @Test(expected = ComparisonFailure.class)
    public void doesnt_match_when_approved_result_doesnt_match() {
        assertFileExists("ApprovalsRuleTest.doesnt_match_when_approved_result_doesnt_match.approved");
        approver.assertApproved("kumquat");
    }

    //README_TEXT

    @Ignore("Unignore to see no approval in IDE")
    @Test public void see_how_my_IDE_reports_no_approval() {
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    @Ignore("Unignore to see failure report in IDE")
    @Test public void see_how_my_IDE_reports_diffs() {
        approver.makeApproved("Deliberate failure - Jackdaws love my big sphinx of quartz");
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    private void assertFileExists(String name) {
        assertTrue(fileFor(name).exists());
    }

    private void assertFileNotExists(String name) {
        assertTrue(fileFor(name).exists());
    }

    private File fileFor(String name) {
        return new File("src/test/java/com/oneeyedmen/okeydoke/examples/" + name);
    }


}
