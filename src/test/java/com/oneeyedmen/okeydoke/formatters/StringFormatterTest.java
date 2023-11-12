package com.oneeyedmen.okeydoke.formatters;

import com.oneeyedmen.okeydoke.junit4.ApprovalsRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

public class StringFormatterTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    private final StringFormatter formatter = new StringFormatter("\"");

    @Test public void a_string_is_itself() {
        approver.assertApproved("A String", formatter);
    }

    @Test public void object_uses_toString() {
        approver.assertApproved(new StringBuilder("A StringBuilder"), formatter);
    }

    @Test public void array_is_listed() {
        approver.assertApproved(new String[] {"one", "two", "three"}, formatter);
    }

    @Test public void iterable_is_listed() {
        approver.assertApproved(Arrays.asList("one", "two", "three"), formatter);
    }

    @Test public void null_is_printed() {
        approver.assertApproved(null, formatter);
    }

    @Test public void emptyIterable() {
        approver.assertApproved(new String[] {}, formatter);
    }
}
