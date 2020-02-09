package com.oneeyedmen.okeydoke.checkers;

import com.oneeyedmen.okeydoke.Checker;

public class StringChecker implements Checker<String> {

    @Override
    public void assertEquals(String expectedMayBeNull, String actualMayBeNull) throws AssertionError {
        Asserter.assertEquals("Actual was not the same as approved", expectedMayBeNull, actualMayBeNull);
    }
}
