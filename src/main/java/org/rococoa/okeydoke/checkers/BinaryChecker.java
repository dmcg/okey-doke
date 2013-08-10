package org.rococoa.okeydoke.checkers;

import org.junit.Assert;
import org.rococoa.okeydoke.Checker;

public class BinaryChecker implements Checker<byte[]> {

    @Override
    public void assertEquals(byte[] expected, byte[] actual) throws AssertionError {
        Assert.assertArrayEquals(expected, actual);
    }
}
