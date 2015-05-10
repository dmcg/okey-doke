package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Checker;
import com.oneeyedmen.okeydoke.Reporter;
import com.oneeyedmen.okeydoke.Serializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A SourceOfApproval that compares the actual to the approved as it is writing the actual.
 *
 * This saves having to read both into memory, improving performance with lots of data,
 * at the expense of less good diff reporting.
 */
public class StreamingFileSystemSourceOfApproval extends FileSystemSourceOfApproval {

    public StreamingFileSystemSourceOfApproval(File directory, Reporter<File> reporter) {
        super(directory, reporter);
    }

    @Override
    public OutputStream outputForActual(String testname) throws IOException {
        InputStream approvedOrNull = inputOrNullForApproved(testname);
        return approvedOrNull == null ?
                super.outputForActual(testname) :
                new ComparingOutputStream(super.outputForActual(testname), approvedOrNull);
    }

    @Override
    public <T> void checkActualAgainstApproved(OutputStream outputStream, String testName, Serializer<T> serializer, Checker<T> checker) throws IOException {
        if (outputStream instanceof ComparingOutputStream) {
            ((ComparingOutputStream) outputStream).assertNoMismatch();
        } else {
            super.checkActualAgainstApproved(outputStream, testName, serializer, checker);
        }
    }
}
