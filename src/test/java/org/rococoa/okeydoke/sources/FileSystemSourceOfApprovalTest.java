package org.rococoa.okeydoke.sources;

import org.junit.Test;
import org.rococoa.okeydoke.Reporters;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FileSystemSourceOfApprovalTest {

    private final FileSystemSourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(
            new File("target/approvals"), Reporters.reporter());

    // This is mostly currently tested by ApproverTest

    @Test public void writes_files_in_package() {
        assertEquals(
                new File("target/approvals", "testname.approved"),
                sourceOfApproval.approvedFor("testname"));
        assertEquals(
                new File("target/approvals", "testname.actual"),
                sourceOfApproval.actualFor("testname"));
    }
}
