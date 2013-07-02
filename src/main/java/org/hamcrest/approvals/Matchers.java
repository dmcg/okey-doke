package org.hamcrest.approvals;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class Matchers {

    static <T> Matcher<T> matches(final ApprovalsRule approvalsRule, final T approved, final String testname) {
        return new IsEqual<T>(approved) {
            @Override
            public boolean matches(Object actual) {
                approvalsRule.writeActual(actual, testname);
                return super.matches(actual);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                System.err.println(approvalsRule.toApproveText(testname));
                super.describeMismatch(item, description);
            }
        };
    }

    static <T> Matcher<T> noApproval(final ApprovalsRule approvalsRule, final String testname) {
        return new TypeSafeDiagnosingMatcher<T>() {
            @Override
            protected boolean matchesSafely(T actual, Description description) {
                approvalsRule.writeActual(actual, testname);
                description.appendText("No approved thing was found.");
                description.appendText(approvalsRule.toApproveText(testname));
                return false;
            }

            public void describeTo(Description description) {
                description.appendText("An approved thing for ").appendValue(testname);
            }
        };
    }

    public static <T> Matcher <T> isAsApproved(ApprovalsRule approvalsRule, String testname) {
        String approved = approvalsRule.readApproved(testname);
        return (Matcher<T>) (approved == null ? noApproval(approvalsRule, testname) : matches(approvalsRule, approved, testname));
    }
}
