package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Resource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class StreamingFileResource extends FileResource {

    private final Resource comparedTo;

    public StreamingFileResource(File file, Resource comparedTo) {
        super(file);
        this.comparedTo = comparedTo;
    }


    @Override
    protected OutputStream outputStreamFor(File file) throws IOException {
        return new ComparingOutputStream(super.outputStream(), comparedTo.inputStream());
    }
}
