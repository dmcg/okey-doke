package org.rococoa.okeydoke.formatters;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.util.Arrays;

public class StringFormatterTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void a_string_is_itself() {
        approver.assertApproved("A String");
    }

    @Test public void object_uses_toString() {
        approver.assertApproved(new StringBuilder("A StringBuilder"));
    }

    @Test public void array_is_listed() {
        approver.assertApproved(new String[] {"one", "two", "three"});
    }

    @Test public void iterable_is_listed() {
        approver.assertApproved(Arrays.asList("one", "two", "three"));
    }

    @Test public void null_is_printed() {
        approver.assertApproved(null);
    }
}
