package org.rococoa.okeydoke;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApproverFileLifecycleTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final FileSystemSourceOfApproval sourceOfApproval = (FileSystemSourceOfApproval) Sources.in("target/approvals");
    private Approver approver;

    @Before public void createApproverInsideCleanDirectoryRule() {
        // required because otherwise the directory is removed after the approver has created its file inside it
        assertFalse(sourceOfApproval.actualFor("testname").exists());
        approver = new Approver("testname", sourceOfApproval, Reporters.reporter());
        assertFalse(sourceOfApproval.actualFor("testname").exists());
    }

    @Test public void approved() throws IOException {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        approver.approve("banana");
        assertTrue(sourceOfApproval.approvedFor("testname").exists());

        approver.assertApproved("banana");
        assertTrue(sourceOfApproval.approvedFor("testname").exists());
        assertFalse(sourceOfApproval.actualFor("testname").exists());
    }

    @Test public void not_approved() throws IOException {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        try {
            approver.assertApproved("banana");
        } catch (AssertionError expected) {}
        assertTrue(sourceOfApproval.approvedFor("testname").exists());
        assertTrue(sourceOfApproval.actualFor("testname").exists());
    }

    @Test public void not_matching_approved() throws IOException {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        approver.approve("banana");
        assertTrue(sourceOfApproval.approvedFor("testname").exists());

        try {
            approver.assertApproved("kumquat");
        } catch (AssertionError expected) {}
        assertTrue(sourceOfApproval.approvedFor("testname").exists());
        assertTrue(sourceOfApproval.actualFor("testname").exists());
    }
}
