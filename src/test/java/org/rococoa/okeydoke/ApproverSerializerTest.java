package org.rococoa.okeydoke;

import org.junit.Test;

import java.io.File;

public class ApproverSerializerTest {

    private final SourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(
            new File("src/test/java"), this.getClass().getPackage(),
            new File("target/approvals"));
    private final Approver approver = new Approver("ApproverSerializerTest", sourceOfApproval);

    @Test public void compare_byte_arrays_successfully() {
        approver.assertApproved("banana".getBytes());
    }

    @Test(expected = AssertionError.class)
    public void compare_byte_arrays_unsuccessfully() {
        approver.assertApproved("kumquat".getBytes());
    }

}
