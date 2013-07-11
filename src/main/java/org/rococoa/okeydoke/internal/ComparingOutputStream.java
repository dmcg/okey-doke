package org.rococoa.okeydoke.internal;

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

    public long firstMismatchPosition() {
        return firstMismatchPosition;
    }

    @Override
    public void write(int b) throws IOException {
        position++;
        int read = is.read();
        if (b != read && firstMismatchPosition == -1)
            firstMismatchPosition = position;
        super.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (int i = off; i < len; i++) {
            write(b[i]);
        }
    }
}
