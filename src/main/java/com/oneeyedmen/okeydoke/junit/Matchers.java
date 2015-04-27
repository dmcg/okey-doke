package com.oneeyedmen.okeydoke.junit;

import com.oneeyedmen.okeydoke.Formatter;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class Matchers {
    public static Matcher<? super Object> isFormatted(final String formattedExpected, final Formatter<Object, String> formatter) {
        return new TypeSafeDiagnosingMatcher<Object>() {
            @Override
            protected boolean matchesSafely(Object item, Description mismatchDescription) {
                return new IsEqual(formattedExpected).matches(formatter.formatted(item));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is formatted as ").appendText(formattedExpected);
            }
        };
    }
}
