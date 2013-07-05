package org.rococoa.okeydoke;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import static org.rococoa.okeydoke.testutils.CleanDirectoryRule.dirForPackage;

public class FormatterTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(dirForPackage("target/approvals", this));
    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java", "target/approvals");

    @Test public void a_string_is_itself() {
        approver.assertApproved("A String");
    }

    @Test public void object_uses_toString() {
        approver.assertApproved(new StringBuilder("A StringBuilder"));
    }

}
