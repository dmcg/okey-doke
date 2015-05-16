package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApproverTest {

    @Rule public final CleanDirectoryRule cleaner = new CleanDirectoryRule(new File("target/approvals"));

    /*
      Note that you won't usually use an Approver directly - ApprovalsRule will manage it for you
     */
    private final Approver approver = new Approver("testname", Sources.in(new File("target/approvals")));

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

    @Test public void matches_when_approved_result_matches() throws IOException {
        whenApprovedIs("banana");
        approver.assertApproved("banana");
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        whenApprovedIs("banana");
        try {
            approver.assertApproved("kumquat");
            fail("should have thrown");
        } catch (ComparisonFailure expected) {
            assertEquals("kumquat", expected.getActual());
            assertEquals("banana", expected.getExpected());
        }
    }

    @Test public void can_assert_with_nothing_approved() throws IOException {
        whenApprovedIs(null);
        approver.assertSatisfied();
    }

    private void whenApprovedIs(String valueOrNull) throws IOException {
        if (valueOrNull == null)
            approver.removeApproved();
        else
            approver.makeApproved(valueOrNull);
    }
}
