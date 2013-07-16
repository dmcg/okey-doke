package org.rococoa.okeydoke.sources;

import org.rococoa.okeydoke.SourceOfApproval;

import java.io.*;

public class FileSystemSourceOfApproval implements SourceOfApproval<File> {

    private final File approvedDir;
    private final File actualDir;

    public FileSystemSourceOfApproval(File directory) {
        this(directory, directory);
    }

    public FileSystemSourceOfApproval(File root, Package aPackage) {
        this(dirForPackage(root, aPackage));
    }

    public FileSystemSourceOfApproval(File srcRoot, Package thePackage, File actualDir) {
        this(dirForPackage(srcRoot, thePackage), actualDir);
    }

    public FileSystemSourceOfApproval(File approvedDir, File actualDir) {
        this.approvedDir = approvedDir;
        this.actualDir = actualDir;
    }

    public static File dirForPackage(File root, Package aPackage) {
        return new File(root, aPackage.getName().replaceAll("\\.", "/"));
    }

    @Override
    public OutputStream outputForApproved(String testname) throws IOException {
        return createAndOpenOutputStreamFor(approvedFor(testname));
    }

    private BufferedOutputStream createAndOpenOutputStreamFor(File file) throws FileNotFoundException {
        file.getParentFile().mkdirs();
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    @Override
    public OutputStream outputForActual(String testname) throws IOException {
        return createAndOpenOutputStreamFor(actualFor(testname));
    }

    @Override
    public InputStream inputOrNullForApproved(String testname) throws FileNotFoundException {
        return InputStreamOrNullFor(approvedFor(testname));
    }

    @Override
    public InputStream inputOrNullForActual(String testname) throws IOException {
        return InputStreamOrNullFor(actualFor(testname));
    }

    @Override
    public void removeActual(String testname) throws IOException {
        actualFor(testname).delete(); // best efforts
    }

    @Override
    public File approvedFor(String testname) {
        return fileFor(approvedDir, testname, approvedExtension());
    }

    private String approvedExtension() {
        return ".approved";
    }

    @Override
    public File actualFor(String testname) {
        return fileFor(actualDir, testname, actualExtension());
    }

    private String actualExtension() {
        return ".actual";
    }

    private File fileFor(File dir, String testname, String suffix) {
        return new File(dir, testname + suffix);
    }

    private InputStream InputStreamOrNullFor(File file) throws FileNotFoundException {
        return !(file.exists() && file.isFile()) ?
                null : new BufferedInputStream(new FileInputStream(file));
    }

}
