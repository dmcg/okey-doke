package org.hamcrest.approvals;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.approvals.internal.IO;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class ApprovalsRule extends TestWatchman {

    private String testName;

    @Override
    public void starting(FrameworkMethod method) {
        testName = method.getName();
    }

    public String testName() {
        return testName;
    }

    public void forgetApproval() {
        fileFor(testName()).delete();
    }

    public Matcher<? super String> isAsApproved() {
        return isAsApproved(testName());
    }

    public void approve(String approved) throws IOException {
        writeApproved(approved, testName());
    }

    public static File fileFor(String testname) {
        return new File(testname);
    }

    public Matcher<? super String> isAsApproved(String testname) {
        String approved = readApproved(testname);
        return  approved == null ? noApproval(testname) : (Matcher<? super String>)equalTo(approved);
    }

    private void writeApproved(String approved, String testname) throws IOException {
        byte[] bytes = approved.getBytes();
        IO.writeBytes(fileFor(testname), bytes);
    }

    private String readApproved(String testname) {
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
