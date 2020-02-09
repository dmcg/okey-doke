package com.oneeyedmen.okeydoke.checkers;

import org.opentest4j.AssertionFailedError;

class Asserter {

    public static void assertEquals(String message, Object expectedMayBeNull, Object actualMayBeNull) {
        if ((expectedMayBeNull != null && !expectedMayBeNull.equals(actualMayBeNull)) ||
            (expectedMayBeNull == null && actualMayBeNull != null))
            throw new AssertionFailedError(message, expectedMayBeNull, actualMayBeNull);
    }
}
