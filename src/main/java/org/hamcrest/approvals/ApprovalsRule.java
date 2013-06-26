package org.hamcrest.approvals;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.approvals.internal.IO;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;

public class ApprovalsRule extends TestWatchman {

    private final String srcRoot;
    private final Object test;

    private String testName;

    public ApprovalsRule(String srcRoot, Object test) {
        this.srcRoot = srcRoot;
        this.test = test;
    }

    @Override
    public void starting(FrameworkMethod method) {
        testName = method.getName();
    }

    public void forgetApproval() {
        fileFor(testName()).delete();
    }

    public <T> Matcher<T> isAsApproved() {
        return isAsApproved(testName());
    }

    public void approve(Object approved) throws IOException {
        writeApproved(approved, testName());
    }

    public String testName() {
        return testName;
    }

    private <T> Matcher <T> isAsApproved(String testname) {
        String approved = readApproved(testname);
        return (Matcher<T>) (approved == null ? noApproval(testname) : equalTo(approved));
    }

    private File fileFor(String testname) {
        return new File(new File(srcRoot, test.getClass().getPackage().getName().replaceAll("\\.", "/")), testname + ".approved");
    }

    private void writeApproved(Object approved, String testname) throws IOException {
        byte[] bytes = approved.toString().getBytes();
        IO.writeBytes(fileFor(testname), bytes);
    }

    private String readApproved(String testname) {
        File approvalFile = fileFor(testname);
        return !(approvalFile.exists() && approvalFile.isFile()) ?
                null : new String(IO.readBytes(approvalFile));
    }

    private static <T> Matcher<T> noApproval(final String testname) {
        return new TypeSafeDiagnosingMatcher<T>() {
            @Override
            protected boolean matchesSafely(T thing, Description description) {
                description.appendText("No approved thing was found");
                return false;
            }

            public void describeTo(Description description) {
                description.appendText("An approved thing for ").appendValue(testname);
            }
        };
    }

    public <T> Matcher<T> FORCE_APPROVAL() {
        return FORCE_APPROVAL(testName());
    }

    public <T> Matcher<T> FORCE_APPROVAL(final String testname) {
        return new TypeSafeDiagnosingMatcher<T>() {
            @Override
            protected boolean matchesSafely(T s, Description description) {
                try {
                    approve(s);
                } catch (IOException e) {
                    description.appendText("Couldn't force approval for ").appendValue(testname);
                    return false;
                }
                return true;
            }

            public void describeTo(Description description) {
                description.appendText("FORCING APPROVAL OF ").appendValue(testName);
            }
        };
    }
}
