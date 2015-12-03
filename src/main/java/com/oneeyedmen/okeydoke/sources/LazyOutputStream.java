package com.oneeyedmen.okeydoke.sources;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Like a FilterOutputStream, but creates its delegate only when first required.
 */
public abstract class LazyOutputStream extends OutputStream {

    private OutputStream _out = null;

    protected abstract OutputStream createOut() throws IOException;

    private OutputStream out() throws IOException {
        if (_out == null) {
            _out = createOut();
        }
        return _out;
    }

    public void write(int b) throws IOException {
        out().write(b);
    }

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        if ((off | len | (b.length - (len + off)) | (off + len)) < 0)
            throw new IndexOutOfBoundsException();

        for (int i = 0 ; i < len ; i++) {
            write(b[off + i]);
        }
    }

    public void flush() throws IOException {
        out().flush();
    }

    public void close() throws IOException {
        try {
            flush();
        } catch (IOException ignored) {
        }
        out().close();
    }
}
