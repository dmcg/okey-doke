package org.hamcrest.approvals;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.approvals.Approvals.*;

public class ApprovalsTest {

    @Test public void doesnt_match_where_no_approved_result() {
        forgetApproval("testname");
        assertThat("banana", not(isAsApproved("testname")));
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approve("banana", "testname");
        assertThat("banana", isAsApproved("testname"));
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approve("banana", "testname");
        assertThat("kumquat", not(isAsApproved("testname")));
    }


}
