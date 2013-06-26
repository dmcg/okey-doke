package org.hamcrest.approvals;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

public class ApprovalsRuleTest {

    @Rule public final ApprovalsRule approver = new ApprovalsRule();

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

    @Test public void squirrels_away_the_test_name_for_us() {
        assertEquals("squirrels_away_the_test_name_for_us", approver.testName());
    }

}
