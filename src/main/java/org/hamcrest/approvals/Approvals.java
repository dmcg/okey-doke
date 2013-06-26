package org.hamcrest.approvals;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.approvals.internal.IO;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class Approvals {

    public static File fileFor(String testname) {
        return new File(testname);
    }

    public static Matcher<? super String> isAsApproved(String testname) {
        String approved = readApproved(testname);
        return (Matcher<? super String>) (approved == null ? noApproval(testname) : equalTo(approved));
    }

    public static void approve(String approved, String testname) throws IOException {
        writeApproved(approved, testname);
    }

    public static void forgetApproval(String testname) {
        fileFor(testname).delete();
    }

    private static void writeApproved(String approved, String testname) throws IOException {
        byte[] bytes = approved.getBytes();
        IO.writeBytes(fileFor(testname), bytes);
    }

    private static String readApproved(String testname) {
        File approvalFile = fileFor(testname);
        return !(approvalFile.exists() && approvalFile.isFile()) ?
            null : new String(IO.readBytes(approvalFile));
    }

    private static Matcher<? super String> noApproval(final String testname) {
        return new TypeSafeDiagnosingMatcher<String>() {
            @Override
            protected boolean matchesSafely(String s, Description description) {
                description.appendText("No approved thing was found");
                return false;
            }

            public void describeTo(Description description) {
                description.appendText("An approved thing for ").appendValue(testname);
            }
        };
    }

}
