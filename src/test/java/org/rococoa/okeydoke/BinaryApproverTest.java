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

public class BinaryApproverTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));
    private BinaryApprover approver;

    @Before
    public void createApproverInsideCleanDirectoryRule() {
        // required because otherwise the directory is removed after the approver has created its file inside it
        approver = new BinaryApprover("testname", Sources.in("target/approvals"));
    }

    @Test(expected = AssertionError.class)
    public void doesnt_match_where_no_approved_result() throws IOException {
        approver.assertApproved("banana".getBytes());
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.approve("banana".getBytes());
        approver.assertApproved("banana".getBytes());
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.approve("banana".getBytes());
        try {
            approver.assertApproved("bnana".getBytes());
            fail();
        } catch (ComparisonFailure failure) {
            assertEquals("62 6E 61 6E 61", failure.getActual());
            assertEquals("62 61 6E 61 6E 61", failure.getExpected());
        }
    }

}
