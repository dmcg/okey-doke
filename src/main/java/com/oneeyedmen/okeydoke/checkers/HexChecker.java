package com.oneeyedmen.okeydoke.checkers;

import com.oneeyedmen.okeydoke.Checker;
import com.oneeyedmen.okeydoke.internal.HexDump;
import org.junit.Assert;

public class HexChecker implements Checker<byte[]> {

    @Override
    public void assertEquals(byte[] expectedOrNull, byte[] actualOrNull) {
        Assert.assertEquals(format(expectedOrNull), format(actualOrNull));
    }

    private String format(byte[] bytesOrNull) {
        return bytesOrNull != null ? HexDump.format(bytesOrNull) : null;
    }

}
