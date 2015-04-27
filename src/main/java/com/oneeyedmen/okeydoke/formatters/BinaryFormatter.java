package com.oneeyedmen.okeydoke.formatters;

import com.oneeyedmen.okeydoke.Formatter;

public class BinaryFormatter implements Formatter<byte[], byte[]> {

    @Override
    public byte[] formatted(byte[] actual) {
        return actual;
    }
}
