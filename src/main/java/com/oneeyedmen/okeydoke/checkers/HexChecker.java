package com.oneeyedmen.okeydoke.checkers;

import com.oneeyedmen.okeydoke.Checker;
import com.oneeyedmen.okeydoke.Checkers;
import com.oneeyedmen.okeydoke.internal.HexDump;

public class HexChecker implements Checker<byte[]> {

    @Override
    public void assertEquals(byte[] expectedOrNull, byte[] actualOrNull) {
        Checkers.stringChecker().assertEquals(format(expectedOrNull), format(actualOrNull));
    }

    private String format(byte[] bytesOrNull) {
        return bytesOrNull != null ? HexDump.format(bytesOrNull) : null;
    }

}
