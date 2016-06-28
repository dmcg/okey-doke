package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Resource;

import java.io.*;

public class FileResource implements Resource {

    private final File file;

    public FileResource(File file) {
        this.file = file;
    }

    @Override
    public OutputStream outputStream() throws IOException {
        return outputStreamFor(file);
    }

    @Override
    public InputStream inputStream() throws IOException {
        return new FileInputStream(file);
    }

    private OutputStream outputStreamFor(final File file) throws FileNotFoundException {
        return new LazyOutputStream() {
            @Override
            protected OutputStream createOut() throws IOException {
                file.getParentFile().mkdirs();
                return new BufferedOutputStream(new FileOutputStream(file));
            }
        };
    }
}
