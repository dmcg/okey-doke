package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Resource;

import java.io.*;

public class FileResource implements Resource {

    private final File file;
    private OutputStream os;

    public FileResource(File file) {
        this.file = file;
    }

    @Override
    public OutputStream outputStream() throws IOException {
        if (os == null) {
            os = outputStreamFor(file);
        }
        return os;
    }

    @Override
    public InputStream inputStream() throws IOException {
        // It feels a bit odd that this isn't memoized, but we don't seem to need it to be
        return new FileInputStream(file);
    }

    @Override
    public void remove() throws IOException {
        file.delete();
        if (exists())
            throw new IOException("Failed to delete " + file);
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public long size() {
        return file.length();
    }

    public File file() {
        return file;
    }

    protected OutputStream outputStreamFor(final File file) throws IOException {
        return new LazyOutputStream() {
            @Override
            protected OutputStream createOut() throws IOException {
                file.getParentFile().mkdirs();
                return new BufferedOutputStream(new FileOutputStream(file));
            }
        };
    }
}
