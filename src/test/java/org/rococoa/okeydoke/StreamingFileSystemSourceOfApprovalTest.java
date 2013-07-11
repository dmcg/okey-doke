package org.rococoa.okeydoke;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.internal.ComparingOutputStream;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StreamingFileSystemSourceOfApprovalTest {

    private final TestDirectory dir = new TestDirectory(StreamingFileSystemSourceOfApprovalTest.class);

    private final StreamingFileSystemSourceOfApproval sourceOfApproval = new StreamingFileSystemSourceOfApproval(dir);

    @Rule public ApprovalsRule rule = new ApprovalsRule() {
        @Override
        protected SourceOfApproval createSourceOfApproval(Class<?> testClass) {
            return sourceOfApproval;
        }
    };

    @Test public void compares_actual_to_approved_as_its_writing() throws IOException {
        Formatter formatter = rule.approver.formatter();
        OutputStream osForApproved = sourceOfApproval.outputForApproved("testname");
        formatter.writeTo("long contents we don't want to read into memory", osForApproved);
        osForApproved.close();

        ComparingOutputStream os = (ComparingOutputStream) sourceOfApproval.outputForActual("testname");
        formatter.writeTo("long CONTENTS we don't want to read into memory", os);
        assertEquals(5, os.firstMismatchPosition());

        os.close();

        try {
            rule.assertSatisfied();
        } catch (Error expected) {}
    }

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
       assertEquals("long CONTENTS we don't want to read into memory", rule.approver.readActual());
    }

}
