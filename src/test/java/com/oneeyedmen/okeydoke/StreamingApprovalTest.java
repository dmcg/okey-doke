package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.junit4.ApprovalsRule;
import com.oneeyedmen.okeydoke.util.TestDirectory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Ignore("for now")
public class StreamingApprovalTest {

    private final TestDirectory dir = new TestDirectory(StreamingApprovalTest.class);
    @Rule public ApprovalsRule rule = new ApprovalsRule(ApproverFactories.streamingApproverFactory(dir));

    @Test public void as_approved() throws IOException {
        rule.makeApproved("long contents we don't want to read into memory");
        rule.assertApproved("long contents we don't want to read into memory");
    }

    @Test(expected = AssertionError.class)
    public void not_approved() {
        rule.assertApproved("long contents we don't want to read into memory");
    }

    @Test
    public void not_as_approved() throws IOException {
        rule.makeApproved("long contents we don't want to read into memory");

        try {
            rule.assertApproved("long CONTENTS we don't want to read into memory");
            fail();
        } catch (AssertionError expected) {
            assertEquals("Streams differed at 5", expected.getMessage());
        }
//       assertEquals("long CONTENTS we don't want to read into memory",
//               (StreamingFileSystemSourceOfApproval) rule.approver()..actualContentOrNull());
    }

}
