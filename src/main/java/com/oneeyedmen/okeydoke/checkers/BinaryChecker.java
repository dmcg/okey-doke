package com.oneeyedmen.okeydoke.checkers;

import com.oneeyedmen.okeydoke.Checker;
import org.opentest4j.AssertionFailedError;

/**
 * A Checker that doesn't render to Hex - it's more efficient but less useful.
 */
public class BinaryChecker implements Checker<byte[]> {

    @Override
    public void assertEquals(byte[] expectedMayBeNull, byte[] actualMayBeNull) throws AssertionError {
        if (expectedMayBeNull == actualMayBeNull)
            return;
        if (expectedMayBeNull == null)
            throw new AssertionFailedError("Actual was not null", expectedMayBeNull, actualMayBeNull);

        Asserter.assertEquals("Actual has unexpected length", expectedMayBeNull.length, actualMayBeNull.length);

        for (int i = 0; i < expectedMayBeNull.length; i++) {
            if (expectedMayBeNull[i] != actualMayBeNull[i]) throw new AssertionFailedError(
                "Actual differs from approved at index " + i, expectedMayBeNull[i], actualMayBeNull[i]);
        }
    }
}
