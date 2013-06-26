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
        approvedFileFor(testName()).delete();
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
        return matches(approved);
    }

    private void writeApproved(Object approved, String testname) throws IOException {
        byte[] bytes = approved.toString().getBytes();
        IO.writeBytes(approvedFileFor(testname), bytes);
    }

    private String readApproved(String testname) {
        File approvalFile = approvedFileFor(testname);
        return !(approvalFile.exists() && approvalFile.isFile()) ?
                null : new String(IO.readBytes(approvalFile));
    }

    private <T> Matcher<T> matches(final Object approved) {
        return new TypeSafeDiagnosingMatcher<T>() {
            @Override
            protected boolean matchesSafely(T thing, Description description) {
                try {
                    IO.writeBytes(actualFile(), thing.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                if (approved == null) {
                    description.appendText("No approved thing was found");
                    return false;
                } else if (approved.equals(thing)) {
                    return true;
                } else {
                    description.appendText("Expected :" + approved);
                    description.appendText("Actual   :" + thing);
                    return false;
                }
            }

            public void describeTo(Description description) {
            }
        };
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

    public File approvedFile() {
        return approvedFileFor(testName());
    }

    private File approvedFileFor(String testname) {
        return fileFor(testname, ".approved");
    }

    public File actualFile() {
        return actualFileFor(testName());
    }

    private File actualFileFor(String testname) {
        return fileFor(testname, ".actual");
    }

    private File fileFor(String testname, String suffix) {
        return new File(new File(srcRoot, test.getClass().getPackage().getName().replaceAll("\\.", "/")), testname + suffix);
    }

}
