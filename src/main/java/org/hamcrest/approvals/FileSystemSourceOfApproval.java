package org.hamcrest.approvals;

import org.hamcrest.approvals.internal.IO;

import java.io.File;
import java.io.IOException;

public class FileSystemSourceOfApproval implements SourceOfApproval {

    private final File directory;

    public FileSystemSourceOfApproval(File directory) {
        this.directory = directory;
    }

    @Override
    public void writeApproved(String testname, byte[] bytes) throws IOException {
        IO.writeBytes(approvedFileFor(testname), bytes);
    }

    @Override
    public String readApproved(String testname) {
        File approvalFile = approvedFileFor(testname);
        return !(approvalFile.exists() && approvalFile.isFile()) ?
                null : new String(IO.readBytes(approvalFile));
    }

    @Override
    public void writeActual(String testname, byte[] bytes) {
        try {
            IO.writeBytes(actualFileFor(testname), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toApproveText(String testname) {
        return String.format("\nTo approve...\ncp %s %s", actualFileFor(testname), approvedFileFor(testname));
    }

    public File approvedFileFor(String testname) {
        return fileFor(testname, ".approved");
    }

    public File actualFileFor(String testname) {
        return fileFor(testname, ".actual");
    }

    private File fileFor(String testname, String suffix) {
        return new File(directory, testname + suffix);
    }


}
