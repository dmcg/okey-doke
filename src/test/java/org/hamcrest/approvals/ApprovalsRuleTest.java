package org.hamcrest.approvals;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.approvals.Approvals.*;
import static org.junit.Assert.assertEquals;

public class ApprovalsRuleTest {

    @Rule public final ApprovalsRule approver = new ApprovalsRule();

    @Test public void squirrels_away_the_test_name_for_us() {
        assertEquals("squirrels_away_the_test_name_for_us", approver.testName());
    }

    @Test public void acts_as_source_of_approval() throws IOException {
        approver.forgetApproval();
        assertThat("banana", not(approver.isApproved()));

        approver.approve("banana");
        assertThat("banana", approver.isApproved());
        assertThat("kumquat", not(approver.isApproved()));
    }

}
