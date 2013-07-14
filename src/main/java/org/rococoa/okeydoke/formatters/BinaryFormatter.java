package org.rococoa.okeydoke.formatters;

import org.junit.Assert;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.internal.HexDump;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This Formatter reads and writes raw bytes, but compares a hex dump.
 */
public class BinaryFormatter implements Formatter<byte[], byte[]> {

    @Override
    public byte[] readFrom(InputStream is) throws IOException {
        byte[] buf = new byte[is.available()];
        is.read(buf);
        return buf;
    }

    @Override
    public byte[] emptyThing() {
        return new byte[0];
    }

    @Override
    public void assertEquals(byte[] expected, byte[] actual) {
        Assert.assertEquals(HexDump.format(expected), HexDump.format(actual));
    }

    @Override
    public byte[] formatted(byte[] actual) {
        return actual;
    }

    @Override
    public void writeTo(byte[] object, OutputStream os) throws IOException {
        os.write(object);
    }
}
