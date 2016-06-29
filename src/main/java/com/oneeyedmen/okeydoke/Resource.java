package com.oneeyedmen.okeydoke;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Resource {
    public OutputStream outputStream() throws IOException;
    public InputStream inputStream() throws IOException;

    public void remove() throws IOException;
    public boolean exists();
    public long size();
}
