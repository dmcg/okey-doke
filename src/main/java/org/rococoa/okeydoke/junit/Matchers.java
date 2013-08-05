package org.rococoa.okeydoke.junit;

import org.hamcrest.Matcher;
import org.rococoa.okeydoke.Formatter;

public class Matchers {
    public static Matcher<? super Object> isFormatted(Object thing, Formatter<Object, String> formatter) {
        return org.hamcrest.Matchers.<Object>equalTo(formatter.formatted(thing));
    }
}
