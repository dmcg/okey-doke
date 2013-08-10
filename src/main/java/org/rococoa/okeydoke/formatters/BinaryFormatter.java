package org.rococoa.okeydoke.formatters;

import org.rococoa.okeydoke.Formatter;

public class BinaryFormatter implements Formatter<byte[], byte[]> {

    @Override
    public byte[] formatted(byte[] actual) {
        return actual;
    }
}
