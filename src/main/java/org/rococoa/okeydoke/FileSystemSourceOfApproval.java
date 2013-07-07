package org.rococoa.okeydoke;

import java.io.*;

public class FileSystemSourceOfApproval implements SourceOfApproval {

    private final File approvedDir;
    private final File actualDir;

    public static FileSystemSourceOfApproval in(String directory) {
        return new FileSystemSourceOfApproval(new File(directory));
    }

    public static FileSystemSourceOfApproval in(String srcRoot, Object o, String actualDir) {
        return new FileSystemSourceOfApproval(
                new File(srcRoot), o.getClass().getPackage(),
                new File(actualDir));
    }

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
    public OutputStream outputForApproved(String testname) throws IOException {
        return createAndOpenOutputStreamFor(approvedFileFor(testname));
    }

    private BufferedOutputStream createAndOpenOutputStreamFor(File file) throws FileNotFoundException {
        file.getParentFile().mkdirs();
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    @Override
    public OutputStream outputForActual(String testname) throws IOException {
        return createAndOpenOutputStreamFor(actualFileFor(testname));
    }

    @Override
    public InputStream inputOrNullForApproved(String testname) throws FileNotFoundException {
        return InputStreamOrNullFor(approvedFileFor(testname));
    }

    @Override
    public InputStream inputOrNullForActual(String testname) throws IOException {
        return InputStreamOrNullFor(actualFileFor(testname));
    }

    @Override
    public String toApproveText(String testname) {
        return String.format("\nTo approve...\ncp %s %s", actualFileFor(testname), approvedFileFor(testname));
    }

    public File approvedFileFor(String testname) {
        return fileFor(approvedDir, testname, approvedExtension());
    }

    private String approvedExtension() {
        return ".approved";
    }

    public File actualFileFor(String testname) {
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
