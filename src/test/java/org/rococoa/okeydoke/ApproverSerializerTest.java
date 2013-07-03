package org.rococoa.okeydoke;

import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

@Ignore("WIP")
public class ApproverSerializerTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final SourceOfApproval sourceOfApproval = new FileSystemSourceOfApproval(
            new File("src/test/java"), this.getClass().getPackage(),
            new File("target/approvals"));
    private final Approver approver = new Approver("testname", sourceOfApproval);

    @Test public void doesnt_match_where_no_approved_result() {
        approver.assertApproved("banana".getBytes());
    }
}
