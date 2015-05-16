package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApprovalsRuleTest {

    //README_TEXT

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test
    public void doesnt_match_where_no_approved_result() throws IOException {
        whenApprovedIs(null);
        try {
            approver.assertApproved("banana");
            fail("should have thrown");
        } catch (ComparisonFailure expected) {
            assertEquals("banana", expected.getActual());
            assertEquals("", expected.getExpected());
        }
    }

    @Test
    public void matches_when_approved_result_matches() throws IOException {
        whenApprovedIs("banana");
        approver.assertApproved("banana");
    }

    @Test
    public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        whenApprovedIs("banana");
        try {
            approver.assertApproved("kumquat");
            fail("should have thrown");
        } catch (ComparisonFailure expected) {
            assertEquals("kumquat", expected.getActual());
            assertEquals("banana", expected.getExpected());
        }
    }

    //README_TEXT

    @Ignore("Unignore to see no approval in IDE")
    @Test public void see_how_my_IDE_reports_no_approval() throws IOException {
        whenApprovedIs(null);
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    @Ignore("Unignore to see failure report in IDE")
    @Test public void see_how_my_IDE_reports_diffs() throws IOException {
        whenApprovedIs("Deliberate failure - Jackdaws love my big sphinx of quartz");
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    private void whenApprovedIs(String valueOrNull) throws IOException {
        if (valueOrNull == null)
            approver.approver().removeApproved();
        else
            approver.makeApproved(valueOrNull);
    }

}
