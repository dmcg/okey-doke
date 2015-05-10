package com.oneeyedmen.okeydoke.sources;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ComparingOutputStream extends FilterOutputStream {

    private final InputStream is;
    private long position = -1;
    private long firstMismatchPosition = -1;

    public ComparingOutputStream(OutputStream out, InputStream is) {
        super(out);
        this.is = is;
    }

    @Override
    public void write(int b) throws IOException {
        position++;
        if (firstMismatchPosition == -1) {
            int read = is.read();
            if (b != read)
                firstMismatchPosition = position;
        }
        super.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (firstMismatchPosition == -1) {
            for (int i = off; i < len; i++) {
                write(b[i]);
            }
        } else {
            super.write(b, off, len);
        }
    }

    @Override
    public void close() throws IOException {
        is.close();
        super.close();
    }

    public void assertNoMismatch() throws AssertionError {
        if (firstMismatchPosition != -1)
            throw new AssertionError("Streams differed at " + firstMismatchPosition);
    }
}
