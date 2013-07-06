package org.rococoa.okeydoke;

import org.junit.Assert;
import org.rococoa.okeydoke.internal.HexDump;

/**
 * This Formatter reads and writes raw bytes, but compares a hex dump.
 */
public class BinaryFormatter implements Formatter<byte[]> {

    @Override
    public byte[] bytesFor(byte[] object) {
        return object;
    }

    @Override
    public byte[] objectFor(byte[] bytes) {
        return bytes;
    }

    @Override
    public void assertEquals(byte[] expected, byte[] actual) {
        Assert.assertEquals(HexDump.format(expected), HexDump.format(actual));
    }

    @Override
    public byte[] formatted(Object actual) {
        return (byte[]) actual;
    }
}
