package org.rococoa.okeydoke.junit;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.rococoa.okeydoke.Formatters.stringFormatter;

public class FormattedMatcherTest {

    @Test public void test() {
        String[] object = {"one, two", "three"};
        String expected = stringFormatter().formatted(object);
        assertThat(expected, org.rococoa.okeydoke.junit.Matchers.isFormatted(object, stringFormatter()));
    }

}
