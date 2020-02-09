package com.oneeyedmen.okeydoke.checkers;

import org.junit.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AsserterTest {

    @Test public void passes_for_null_and_null() {
        Asserter.assertEquals("message", null, null);
    }

    @Test public void passes_for_equal_strings() {
        Asserter.assertEquals("message", "banana", "banana");
    }

    @Test public void fails_for_null_and_string() {
        try {
            Asserter.assertEquals("message", null, "banana");
        } catch (AssertionFailedError exception) {
            assertEquals("message", exception.getMessage());
            assertNull(exception.getExpected().getValue());
            assertEquals("banana", exception.getActual().getValue());
        }
    }

    @Test public void fails_for_string_and_null() {
        try {
            Asserter.assertEquals("message", "banana", null);
        } catch (AssertionFailedError exception) {
            assertEquals("message", exception.getMessage());
            assertEquals("banana", exception.getExpected().getValue());
            assertNull(exception.getActual().getValue());
        }
    }

    @Test public void fails_for_string_and_a_different_string() {
        try {
            Asserter.assertEquals("message", "banana", "kumquat");
        } catch (AssertionFailedError exception) {
            assertEquals("message", exception.getMessage());
            assertEquals("banana", exception.getExpected().getValue());
            assertEquals("kumquat", exception.getActual().getValue());
        }
    }
}