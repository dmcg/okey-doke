package org.rococoa.okeydoke;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.util.TestDirectory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StreamingApprovalTest {

    private final TestDirectory dir = new TestDirectory(StreamingApprovalTest.class);
    @Rule public ApprovalsRule rule = new ApprovalsRule(ApproverFactories.streamingFileSystemApprover(dir));

    @Test public void as_approved() throws IOException {
        rule.approve("long contents we don't want to read into memory");
        rule.assertApproved("long contents we don't want to read into memory");
    }

    @Test(expected = AssertionError.class)
    public void not_approved() throws IOException {
        rule.assertApproved("long contents we don't want to read into memory");
    }

    @Test
    public void not_as_approved() throws IOException {
        rule.approve("long contents we don't want to read into memory");

        try {
            rule.assertApproved("long CONTENTS we don't want to read into memory");
            fail();
        } catch (AssertionError expected) {
            assertEquals("Streams differed at 5", expected.getMessage());
        }
       assertEquals("long CONTENTS we don't want to read into memory", rule.approver().readActual());
    }

}
