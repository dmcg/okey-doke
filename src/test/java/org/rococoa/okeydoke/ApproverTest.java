package org.rococoa.okeydoke;

import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApproverTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final Approver approver = new Approver("testname", FileSystemSourceOfApproval.in("target/approvals"));

    @Test(expected = AssertionError.class)
    public void doesnt_match_where_no_approved_result() {
        approver.assertApproved("banana");
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.approve("banana");
        approver.assertApproved("banana");
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.approve("banana");
        try {
            approver.assertApproved("kumquat");
            fail();
        } catch (ComparisonFailure expected) {
            assertEquals("kumquat", expected.getActual());
            assertEquals("banana", expected.getExpected());
        }
    }

    @Test public void approve_binary() throws IOException {
        try {
            approver.assertBinaryApproved("banana".getBytes());
            fail();
        } catch (AssertionError expected) {
        }

        approver.approveBinary("banana".getBytes());
        approver.assertBinaryApproved("banana".getBytes());

        try {
            approver.assertBinaryApproved("kumquat".getBytes());
            fail();
        } catch (AssertionError expected) {
        }

    }
}
