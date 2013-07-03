package org.rococoa.okeydoke;

import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ApproverTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final FileSystemSourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(new File("target/approvals"));
    private final Approver approver = new Approver("testname", sourceOfApproval);

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

    @Test public void files_lifecycle_when_approved() throws IOException {
        assertFalse(sourceOfApproval.approvedFileFor("testname").exists());
        assertFalse(sourceOfApproval.actualFileFor("testname").exists());

        approver.approve("banana");
        assertTrue(sourceOfApproval.approvedFileFor("testname").exists());
        assertFalse(sourceOfApproval.actualFileFor("testname").exists());

        approver.assertApproved("banana");
        assertTrue(sourceOfApproval.approvedFileFor("testname").exists());
        assertTrue(sourceOfApproval.actualFileFor("testname").exists());
    }

    @Test public void files_lifecycle_when_not_approved() throws IOException {
        assertFalse(sourceOfApproval.approvedFileFor("testname").exists());
        assertFalse(sourceOfApproval.actualFileFor("testname").exists());

        try {
            approver.assertApproved("banana");
        } catch (AssertionError expected) {}
        assertFalse(sourceOfApproval.approvedFileFor("testname").exists());
        assertTrue(sourceOfApproval.actualFileFor("testname").exists());
    }

    @Test public void files_lifecycle_when_not_matching_approved() throws IOException {
        assertFalse(sourceOfApproval.approvedFileFor("testname").exists());
        assertFalse(sourceOfApproval.actualFileFor("testname").exists());

        approver.approve("banana");
        assertTrue(sourceOfApproval.approvedFileFor("testname").exists());
        assertFalse(sourceOfApproval.actualFileFor("testname").exists());

        try {
            approver.assertApproved("kumquat");
        } catch (AssertionError expected) {}
        assertTrue(sourceOfApproval.approvedFileFor("testname").exists());
        assertTrue(sourceOfApproval.actualFileFor("testname").exists());
    }
}
