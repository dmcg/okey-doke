package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.sources.FileSystemSourceOfApproval;
import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ApproverFileLifecycleTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final FileSystemSourceOfApproval sourceOfApproval = Sources.in(new File("target/approvals"));
    private Approver approver;

    @Before public void createApproverInsideCleanDirectoryRule() {
        // required because otherwise the directory is removed after the approver has created its file inside it
        assertFalse(sourceOfApproval.actualFor("testname").exists());
        approver = new Approver("testname", sourceOfApproval);
        assertFalse(sourceOfApproval.actualFor("testname").exists());
    }

    @Test public void approved() {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        approver.makeApproved("banana");
        assertEquals("banana".length(), sourceOfApproval.approvedFor("testname").length());

        approver.assertApproved("banana");
        assertTrue(sourceOfApproval.approvedFor("testname").exists());
        assertFalse(sourceOfApproval.actualFor("testname").exists());
    }

    @Test public void not_approved() {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        try {
            approver.assertApproved("banana");
        } catch (AssertionError expected) {}
        assertEquals(0, sourceOfApproval.approvedFor("testname").length());
        assertEquals("banana".length(), sourceOfApproval.actualFor("testname").length());
    }

    @Test public void not_matching_approved() {
        assertFalse(sourceOfApproval.approvedFor("testname").exists());

        approver.makeApproved("banana");
        assertTrue(sourceOfApproval.approvedFor("testname").exists());

        try {
            approver.assertApproved("kumquat");
        } catch (AssertionError expected) {}
        assertEquals("banana".length(), sourceOfApproval.approvedFor("testname").length());
        assertEquals("kumquat".length(), sourceOfApproval.actualFor("testname").length());
    }
}
