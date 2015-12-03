package com.oneeyedmen.okeydoke.checkers;

import com.oneeyedmen.okeydoke.Checker;
import org.junit.Assert;

public class BinaryChecker implements Checker<byte[]> {

    @Override
    public void assertEquals(byte[] expectedMayBeNull, byte[] actualMayBeNull) throws AssertionError {
        Assert.assertArrayEquals(expectedMayBeNull, actualMayBeNull);
    }
}
