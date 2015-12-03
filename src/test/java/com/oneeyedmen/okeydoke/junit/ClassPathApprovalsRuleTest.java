package com.oneeyedmen.okeydoke.junit;

import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ClassPathApprovalsRuleTest {

    // The approved files here are in src/test/resources
    @Rule public final ApprovalsRule approver = ApprovalsRule.classPathRule("target/approvals");

    @Test
    public void doesnt_match_where_no_approved_result() throws IOException {
        try {
            approver.assertApproved("banana");
            fail();
        } catch (AssertionError expected) {
        }
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.assertApproved("banana");
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        try {
            approver.assertApproved("kumquat");
            fail();
        } catch (ComparisonFailure expected) {
            assertEquals("kumquat", expected.getActual());
            assertEquals("banana", expected.getExpected());
        }
    }
}
