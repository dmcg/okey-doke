package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Checker;
import com.oneeyedmen.okeydoke.Reporter;
import com.oneeyedmen.okeydoke.Resource;
import com.oneeyedmen.okeydoke.Serializer;

import java.io.File;
import java.io.IOException;

/**
 * A SourceOfApproval that compares the actual to the approved as it is writing the actual.
 *
 * This saves having to read both into memory, improving performance with lots of data,
 * at the expense of less good diff reporting.
 */
public class StreamingFileSystemSourceOfApproval extends FileSystemSourceOfApproval {

    public StreamingFileSystemSourceOfApproval(File directory, Reporter<File, File> reporter) {
        super(directory, reporter);
    }

    @Override
    public Resource actualFor(String testname) {
        File file = approvedFileFor(testname);
        return file.exists() && file.isFile()
                ? new StreamingFileResource(actualFileFor(testname), new FileResource(approvedFileFor(testname)))
                : new FileResource(actualFileFor(testname));
    }

    @Override
    public <T> void checkActualAgainstApproved(String testName, Serializer<T> serializer, Checker<T> checker) throws IOException {
        Resource actual = actualFor(testName);
        if (actual instanceof StreamingFileResource) {
            ((ComparingOutputStream) actual.outputStream()).assertNoMismatch();
        } else {
            super.checkActualAgainstApproved(testName, serializer, checker);
        }
    }
}
