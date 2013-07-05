package org.rococoa.okeydoke;

import org.junit.Assert;

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
        Assert.assertArrayEquals(expected, actual);
    }

    @Override
    public byte[] formatted(Object actual) {
        return (byte[]) actual;
    }
}
