package org.hamcrest.approvals;

import org.hamcrest.approvals.rules.CleanDirectoryRule;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.approvals.rules.CleanDirectoryRule.dirForPackage;
import static org.junit.Assert.fail;

public class MatcherTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(dirForPackage("target/approvals", this));

    @Rule public final ApprovalsRule approver = new ApprovalsRule("target/approvals");

    @Test public void doesnt_match_where_no_approved_result() {
        assertThat("banana", not(approver.isAsApproved()));
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.approve("banana");
        assertThat("banana", approver.isAsApproved());
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.approve("banana");
        assertThat("kumquat", not(approver.isAsApproved()));
    }

    @Test public void look_at_the_nice_messages_from_hamcrest() throws IOException {
        try {
            Assert.assertThat("Deliberate failure - Jackdaws peck my big sphincter of quartz", approver.isAsApproved());
            fail("Unexpected non-assertion");
        } catch (AssertionError expected) {
            System.out.println(expected);
        }

        approver.approve("Deliberate failure - Jackdaws love my big sphinx of quartz");
        try {
            Assert.assertThat("Deliberate failure - Jackdaws peck my big sphincter of quartz", approver.isAsApproved());
            fail("Unexpected non-assertion");
        } catch (AssertionError expected) {
            System.out.println(expected);
        }
    }

    @Ignore("Unignore to see failure report in IDE")
    @Test public void see_how_my_IDE_reports_diffs() throws IOException {
        approver.approve("Deliberate failure - Jackdaws love my big sphinx of quartz");
        Assert.assertThat("Deliberate failure - Jackdaws peck my big sphincter of quartz", approver.isAsApproved());
    }

    @Test public void can_force_approval_by_editing_the_matcher() throws IOException {
        Assert.assertThat("banana", approver.FORCE_APPROVAL());
        Assert.assertThat("banana", approver.isAsApproved());
    }
}
