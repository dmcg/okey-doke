package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Reporters;
import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class FileSystemSourceOfApprovalTest {

    @Rule
    public final CleanDirectoryRule cleaner = new CleanDirectoryRule(new File("target/approvals"));

    private final FileSystemSourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(
            new File("target/approvals"), Reporters.fileSystemReporter());

    // This is mostly currently tested by ApproverFileLifecycleTest

    @Test public void writes_files_in_package() {
        assertEquals(
                new File("target/approvals", "testname.approved"),
                ((FileResource) sourceOfApproval.approvedFor("testname")).file());
        assertEquals(
                new File("target/approvals", "testname.actual"),
                ((FileResource) sourceOfApproval.actualFor("testname")).file());
    }

    @Test public void doesnt_create_files_until_written_to() throws IOException {
        OutputStream stream = sourceOfApproval.actualFor("testname").outputStream();
        assertFalse(((FileResource) sourceOfApproval.actualFor("testname")).file().isFile());

        stream.write("hello".getBytes());
        assertTrue(((FileResource) sourceOfApproval.actualFor("testname")).file().isFile());
    }
}
