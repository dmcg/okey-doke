package com.oneeyedmen.okeydoke.checkers;

import com.oneeyedmen.okeydoke.Checker;
import com.oneeyedmen.okeydoke.internal.HexDump;
import org.junit.Assert;

public class HexChecker implements Checker<byte[]> {

    @Override
    public void assertEquals(byte[] expected, byte[] actual) {
        Assert.assertEquals(HexDump.format(expected), HexDump.format(actual));
    }

}
