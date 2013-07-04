package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.IO;

import java.io.File;
import java.io.IOException;

public class FileSystemSourceOfApproval implements SourceOfApproval {

    private final File approvedDir;
    private final File actualDir;

    public FileSystemSourceOfApproval(File directory) {
        this(directory, directory);
    }

    public FileSystemSourceOfApproval(File srcRoot, Package packege, File actualDir) {
        this(dirForPackage(srcRoot, packege), actualDir);
    }

    public FileSystemSourceOfApproval(File approvedDir, File actualDir) {
        this.approvedDir = approvedDir;
        this.actualDir = actualDir;
    }

    public FileSystemSourceOfApproval(File root, Package packege) {
        this(dirForPackage(root, packege));
    }

    public static File dirForPackage(File root, Package packege) {
        return new File(root, packege.getName().replaceAll("\\.", "/"));
    }

    @Override
    public void writeApproved(String testname, byte[] bytes) throws IOException {
        IO.writeBytes(approvedFileFor(testname), bytes);
    }

    @Override
    public byte[] readApproved(String testname) {
        File approvalFile = approvedFileFor(testname);
        return !(approvalFile.exists() && approvalFile.isFile()) ?
                null : IO.readBytes(approvalFile);
    }

    @Override
    public CompareResult writeAndCompare(String testname, byte[] actual) {
        writeActual(testname, actual);

        // we're not going to try to compare, just return approved
        return new CompareResult(null, readApproved(testname));
    }

    protected void writeActual(String testname, byte[] bytes) {
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
        return fileFor(approvedDir, testname, ".approved");
    }

    public File actualFileFor(String testname) {
        return fileFor(actualDir, testname, ".actual");
    }

    private File fileFor(File dir, String testname, String suffix) {
        return new File(dir, testname + suffix);
    }


}
