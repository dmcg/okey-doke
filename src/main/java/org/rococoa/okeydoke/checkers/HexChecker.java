package org.rococoa.okeydoke.checkers;

import org.junit.Assert;
import org.rococoa.okeydoke.Checker;
import org.rococoa.okeydoke.internal.HexDump;

public class HexChecker implements Checker<byte[]> {

    @Override
    public void assertEquals(byte[] expected, byte[] actual) {
        Assert.assertEquals(HexDump.format(expected), HexDump.format(actual));
    }

}
