package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.Rule;
import org.junit.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BinaryApproverTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));
    private final BinaryApprover approver = new BinaryApprover("testname", Sources.in(new File("target/approvals")));

    @Test
    public void doesnt_match_where_no_approved_result() {
        try {
            approver.assertApproved("banana".getBytes());
            fail();
        } catch (AssertionFailedError failure) {
        }
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.makeApproved("banana".getBytes());
        approver.assertApproved("banana".getBytes());
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.makeApproved("banana".getBytes());
        try {
            approver.assertApproved("bnana".getBytes());
            fail();
        } catch (AssertionFailedError failure) {
            assertEquals("62 6E 61 6E 61", failure.getActual().getValue());
            assertEquals("62 61 6E 61 6E 61", failure.getExpected().getValue());
        }
    }

}
