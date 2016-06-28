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
        return new FileInputStream(file);
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
