package org.rococoa.okeydoke.sources;

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

    public StreamingFileSystemSourceOfApproval(File directory) {
        super(directory);
    }

    public StreamingFileSystemSourceOfApproval(File srcRoot, Package thePackage, File actualDir) {
        super(srcRoot, thePackage, actualDir);
    }

    public StreamingFileSystemSourceOfApproval(File approvedDir, File actualDir) {
        super(approvedDir, actualDir);
    }

    public StreamingFileSystemSourceOfApproval(File root, Package thePackage) {
        super(root, thePackage);
    }

    @Override
    public OutputStream outputForActual(String testname) throws IOException {
        InputStream approvedOrNull = inputOrNullForApproved(testname);
        return approvedOrNull == null ?
            super.outputForActual(testname) :
            new ComparingOutputStream(super.outputForActual(testname), approvedOrNull);
    }
}
