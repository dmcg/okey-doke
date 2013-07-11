package org.rococoa.okeydoke.sources;

import org.rococoa.okeydoke.FileSystemSourceOfApproval;
import org.rococoa.okeydoke.internal.ComparingOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamingFileSystemSourceOfApproval extends FileSystemSourceOfApproval {

    public StreamingFileSystemSourceOfApproval(File directory) {
        super(directory);
    }

    public StreamingFileSystemSourceOfApproval(File srcRoot, Package packege, File actualDir) {
        super(srcRoot, packege, actualDir);
    }

    public StreamingFileSystemSourceOfApproval(File approvedDir, File actualDir) {
        super(approvedDir, actualDir);
    }

    public StreamingFileSystemSourceOfApproval(File root, Package packege) {
        super(root, packege);
    }

    @Override
    public OutputStream outputForActual(String testname) throws IOException {
        InputStream approvedOrNull = inputOrNullForApproved(testname);
        return approvedOrNull == null ?
            super.outputForActual(testname) :
            new ComparingOutputStream(super.outputForActual(testname), approvedOrNull);
    }
}
