package org.hamcrest.approvals;

import org.hamcrest.approvals.rules.CleanDirectoryRule;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ApproverTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final Approver approver = new Approver(new File("target/approvals"), "testname");

    @Test public void doesnt_match_where_no_approved_result() {
        try {
            approver.assertApproved("banana");
            fail();
        } catch (AssertionError expected) {}
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
        } catch (ComparisonFailure expected) {}
    }

    @Test public void writes_files_in_package() throws IOException {
        assertEquals(
                new File("target/approvals", "testname.approved"),
                approver.approvedFile());
        assertEquals(
                new File("target/approvals", "testname.actual"),
                approver.actualFile());
    }

    @Test public void files_lifecycle_when_approved() throws IOException {
        assertFalse(approver.approvedFile().exists());
        assertFalse(approver.actualFile().exists());

        approver.approve("banana");
        assertTrue(approver.approvedFile().exists());
        assertFalse(approver.actualFile().exists());

        approver.assertApproved("banana");
        assertTrue(approver.approvedFile().exists());
        assertTrue(approver.actualFile().exists());
    }

    @Test public void files_lifecycle_when_not_approved() throws IOException {
        assertFalse(approver.approvedFile().exists());
        assertFalse(approver.actualFile().exists());

        try {
            approver.assertApproved("banana");
        } catch (AssertionError expected) {}
        assertFalse(approver.approvedFile().exists());
        assertTrue(approver.actualFile().exists());
    }

    @Test public void files_lifecycle_when_not_matching_approved() throws IOException {
        assertFalse(approver.approvedFile().exists());
        assertFalse(approver.actualFile().exists());

        approver.approve("banana");
        assertTrue(approver.approvedFile().exists());
        assertFalse(approver.actualFile().exists());

        try {
            approver.assertApproved("kumquat");
        } catch (AssertionError expected) {}
        assertTrue(approver.approvedFile().exists());
        assertTrue(approver.actualFile().exists());
    }
}
