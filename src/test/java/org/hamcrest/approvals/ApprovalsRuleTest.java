package org.hamcrest.approvals;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class ApprovalsRuleTest {

    @Rule public final ApprovalsRule approver = new ApprovalsRule("src/test/java", this);

    @Test public void doesnt_match_where_no_approved_result() {
        approver.forgetApproval();
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
        approver.forgetApproval();
        try {
            Assert.assertThat("Deliberate failure - Jackdaws adore my big sphincter of quartz", approver.isAsApproved());
            fail("Unexpected non-assertion");
        } catch (AssertionError expected) {
            System.out.println(expected);
        }

        approver.approve("Deliberate failure - Jackdaws love my big sphinx of quartz");
        try {
            Assert.assertThat("Deliberate failure - Jackdaws adore my big sphincter of quartz", approver.isAsApproved());
            fail("Unexpected non-assertion");
        } catch (AssertionError expected) {
            System.out.println(expected);
        }
    }

    @Test public void can_force_approval_by_editing_the_matcher() throws IOException {
        approver.forgetApproval();
        Assert.assertThat("banana", approver.FORCE_APPROVAL());
        Assert.assertThat("banana", approver.isAsApproved());
    }

    @Test public void squirrels_away_the_test_name_for_us() {
        assertEquals("squirrels_away_the_test_name_for_us", approver.testName());
    }

}
