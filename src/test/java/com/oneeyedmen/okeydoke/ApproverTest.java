package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

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
            approver.assertApproved("kumquat");
            fail("should have thrown");
        } catch (AssertionFailedError exception) {
            assertNull(exception.getExpected().getValue());
            assertEquals("kumquat", exception.getActual().getStringRepresentation());
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
        } catch (AssertionFailedError exception) {
            assertEquals("banana", exception.getExpected().getValue());
            assertEquals("kumquat", exception.getActual().getValue());
        }
    }

    @Ignore("Unignore to see no failure in IDE")
    @Test public void what_does_intellij_say() throws IOException {
        whenApprovedIs("banana");
        approver.assertApproved("kumquat");
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
