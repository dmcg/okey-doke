package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

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
        } catch (ComparisonFailure failure) {
            assertEquals("62 61 6E 61 6E 61", failure.getActual());
            assertEquals("", failure.getExpected());
        }
    }

    @Test public void matches_when_approved_result_matches() {
        approver.makeApproved("banana".getBytes());
        approver.assertApproved("banana".getBytes());
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() {
        approver.makeApproved("banana".getBytes());
        try {
            approver.assertApproved("bnana".getBytes());
            fail();
        } catch (ComparisonFailure failure) {
            assertEquals("62 6E 61 6E 61", failure.getActual());
            assertEquals("62 61 6E 61 6E 61", failure.getExpected());
        }
    }

}
