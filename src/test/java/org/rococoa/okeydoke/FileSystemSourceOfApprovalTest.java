package org.rococoa.okeydoke;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileSystemSourceOfApprovalTest {

    private final FileSystemSourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(new File("target/approvals"));

    // This is mostly currently tested by ApproverTest

    @Test public void writes_files_in_package() throws IOException {
        assertEquals(
                new File("target/approvals", "testname.approved"),
                sourceOfApproval.approvedFileFor("testname"));
        assertEquals(
                new File("target/approvals", "testname.actual"),
                sourceOfApproval.actualFileFor("testname"));
    }
}
