package org.rococoa.okeydoke.formatters;

import org.junit.Assert;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.internal.HexDump;

/**
 * This Formatter compares a hex dump.
 */
public class BinaryFormatter implements Formatter<byte[], byte[]> {

    @Override
    public void assertEquals(byte[] expected, byte[] actual) {
        Assert.assertEquals(HexDump.format(expected), HexDump.format(actual));
    }

    @Override
    public byte[] formatted(byte[] actual) {
        return actual;
    }
}
