package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.sources.FileSystemSourceOfApproval;
import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ApproverFileLifecycleTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final FileSystemSourceOfApproval sourceOfApproval = Sources.in(new File("target/approvals"));
    private Approver approver;

    @Before public void createApproverInsideCleanDirectoryRule() {
        // required because otherwise the directory is cleaned after the approver has created its file inside it
        assertFalse(sourceOfApproval.actualFor("testname").exists());
        approver = new Approver("testname", sourceOfApproval);
        assertFalse(sourceOfApproval.actualFor("testname").exists());
    }

    @Test public void approved_removes_actual_file() throws IOException {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        approver.makeApproved("banana");
        assertEquals("banana".length(), sourceOfApproval.approvedFor("testname").length());

        approver.assertApproved("banana");
        assertTrue(sourceOfApproval.approvedFor("testname").exists());
        assertFalse(sourceOfApproval.actualFor("testname").exists());
    }

    @Test public void creates_approved_file_when_there_is_none_to_give_a_diff() {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        try {
            approver.assertApproved("banana");
        } catch (AssertionError expected) {}
        assertEquals(0, sourceOfApproval.approvedFor("testname").length());
        assertEquals("banana".length(), sourceOfApproval.actualFor("testname").length());
    }

    @Test public void both_files_present_when_doesnt_match_approved() throws IOException {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        approver.makeApproved("banana");
        assertTrue(sourceOfApproval.approvedFor("testname").exists());

        try {
            approver.assertApproved("kumquat");
        } catch (AssertionError expected) {}
        assertEquals("banana".length(), sourceOfApproval.approvedFor("testname").length());
        assertEquals("kumquat".length(), sourceOfApproval.actualFor("testname").length());
    }

    @Test public void neither_file_created_if_no_assertion() throws IOException {
        assertFalse(sourceOfApproval.actualFor("testname").exists());
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        approver.assertSatisfied();
        assertFalse(sourceOfApproval.actualFor("testname").exists());
        assertFalse(sourceOfApproval.approvedFor("testname").exists());
    }

    @Test public void approved_not_removed_if_no_assertion() throws IOException {
        approver.makeApproved("banana");
        assertFalse(sourceOfApproval.actualFor("testname").exists());
        assertTrue(sourceOfApproval.approvedFor("testname").exists());

        try {
            approver.assertSatisfied();
        } catch (AssertionError expected) {}

        assertFalse(sourceOfApproval.actualFor("testname").exists());
        assertTrue(sourceOfApproval.approvedFor("testname").exists());
    }

}
