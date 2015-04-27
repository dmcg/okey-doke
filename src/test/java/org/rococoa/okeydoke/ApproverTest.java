package org.rococoa.okeydoke;

import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApproverTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));
    private final Approver approver = new Approver("testname", Sources.in(new File("target/approvals")));

    /*
      Note that you won't usually use an Approver directly - ApprovalsRule will manage it for you
     */

    @Test
    public void doesnt_match_where_no_approved_result()  {
        try {
            approver.assertApproved("banana");
            fail();
        } catch (ComparisonFailure expected) {
            assertEquals("banana", expected.getActual());
            assertEquals("", expected.getExpected());
        }
    }

    @Test public void matches_when_approved_result_matches() {
        approver.makeApproved("banana");
        approver.assertApproved("banana");
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() {
        approver.makeApproved("banana");
        try {
            approver.assertApproved("kumquat");
            fail();
        } catch (ComparisonFailure expected) {
            assertEquals("kumquat", expected.getActual());
            assertEquals("banana", expected.getExpected());
        }
    }

    @Test public void can_assert_with_nothing_approved() {
        approver.makeApproved("");
        approver.assertSatisfied();
    }
}
