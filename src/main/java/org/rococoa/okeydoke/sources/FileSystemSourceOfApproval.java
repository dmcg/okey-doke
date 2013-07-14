package org.rococoa.okeydoke.sources;

import org.rococoa.okeydoke.SourceOfApproval;

import java.io.*;

public class FileSystemSourceOfApproval implements SourceOfApproval {

    private final File approvedDir;
    private final File actualDir;
    private final String differ;

    public FileSystemSourceOfApproval(File directory) {
        this(directory, directory);
    }

    public FileSystemSourceOfApproval(File srcRoot, Package thePackage, File actualDir) {
        this(dirForPackage(srcRoot, thePackage), actualDir);
    }

    public FileSystemSourceOfApproval(File approvedDir, File actualDir) {
        this(approvedDir, actualDir, "diff");
    }

    public FileSystemSourceOfApproval(File approvedDir, File actualDir, String differ) {
        this.approvedDir = approvedDir;
        this.actualDir = actualDir;
        this.differ = differ;
    }

    public FileSystemSourceOfApproval(File root, Package aPackage) {
        this(dirForPackage(root, aPackage));
    }

    public static File dirForPackage(File root, Package aPackage) {
        return new File(root, aPackage.getName().replaceAll("\\.", "/"));
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
        return String.format("To approve...\ncp %s %s", actualFileFor(testname), approvedFileFor(testname));
    }

    @Override
    public void reportFailure(String testname, Throwable e) {
        System.err.println("To see differences...");
        System.err.println(diffCommandFor(actualFileFor(testname), approvedFileFor(testname)));
    }

    @Override
    public void removeActual(String testname) throws IOException {
        actualFileFor(testname).delete(); // best efforts
    }

    protected String diffCommandFor(File actual, File approved) {
        return differ() + " '" + actual + "' '" + approved + "'";
    }

    protected String differ() {
        return differ;
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
