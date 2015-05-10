package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Reporters;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FileSystemSourceOfApprovalTest {

    private final FileSystemSourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(
            new File("target/approvals"), Reporters.fileSystemReporter());

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
