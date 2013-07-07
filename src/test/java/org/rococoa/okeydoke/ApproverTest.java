package org.rococoa.okeydoke;

import org.junit.Before;
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
    private Approver approver;

    @Before
    public void createApproverInsideCleanDirectoryRule() {
        // required because otherwise the directory is removed after the approver has created its file inside it
        approver = new Approver("testname", FileSystemSourceOfApproval.in("target/approvals"));
    }

    @Test(expected = AssertionError.class)
    public void doesnt_match_where_no_approved_result() throws IOException {
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

    @Test(expected = AssertionError.class)
    public void binary_doesnt_match_where_no_approved_result() throws IOException {
        approver.assertBinaryApproved("banana".getBytes());
    }

    @Test public void binary_matches_when_approved_result_matches() throws IOException {
        approver.approveBinary("banana".getBytes());
        approver.assertBinaryApproved("banana".getBytes());
    }

    @Test public void binary_doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.approveBinary("banana".getBytes());
        try {
            approver.assertBinaryApproved("bnana".getBytes());
            fail();
        } catch (ComparisonFailure failure) {
            assertEquals("62 6E 61 6E 61", failure.getActual());
            assertEquals("62 61 6E 61 6E 61", failure.getExpected());
        }
    }

}
