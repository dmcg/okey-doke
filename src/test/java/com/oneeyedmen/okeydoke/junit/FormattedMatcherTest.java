package com.oneeyedmen.okeydoke.junit;

import org.junit.Test;

import static com.oneeyedmen.okeydoke.Formatters.stringFormatter;
import static org.hamcrest.MatcherAssert.assertThat;

public class FormattedMatcherTest {

    @Test public void test() {
        String[] object = {"one, two", "three"};
        String expected = stringFormatter().formatted(object);
        assertThat(object, Matchers.isFormatted(expected, stringFormatter()));
    }

}
