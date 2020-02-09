package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.testutils.CleanDirectoryRule;
import org.junit.Rule;
import org.junit.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BinaryApproverWithBinaryCheckerTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(new File("target/approvals"));

    private final BaseApprover<byte[], byte[]> approver = new BaseApprover<>(
        "testname",
        Sources.in(new File("target/approvals")),
        Formatters.binaryFormatter(),
        Serializers.binarySerializer(),
        Checkers.binaryChecker()
    );

    @Test
    public void doesnt_match_where_no_approved_result() {
        try {
            approver.assertApproved("banana".getBytes());
            fail();
        } catch (AssertionFailedError failure) {
        }
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.makeApproved("banana".getBytes());
        approver.assertApproved("banana".getBytes());
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match_size() throws IOException {
        approver.makeApproved("banana".getBytes());
        try {
            approver.assertApproved("bnana".getBytes());
            fail();
        } catch (AssertionFailedError failure) {
            assertEquals("Actual has unexpected length", failure.getMessage());
            assertEquals(6, failure.getExpected().getValue());
            assertEquals(5, failure.getActual().getValue());
        }
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match_content() throws IOException {
        approver.makeApproved("banana".getBytes());
        try {
            approver.assertApproved("bananb".getBytes());
            fail();
        } catch (AssertionFailedError failure) {
            assertEquals("Actual differs from approved at index 5", failure.getMessage());
            assertEquals((byte) 97, failure.getExpected().getValue());
            assertEquals((byte) 98, failure.getActual().getValue());
        }
    }
}
