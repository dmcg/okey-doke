package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Reporter;
import com.oneeyedmen.okeydoke.SourceOfApproval;

import java.io.*;

public class FileSystemSourceOfApproval implements SourceOfApproval<File> {

    private final Reporter<File> reporter;
    private File approvedDir;
    private File actualDir;
    private String typeExtension = "";

    public FileSystemSourceOfApproval(File directory, Reporter<File> reporter) {
        this.approvedDir = directory;
        this.actualDir = directory;
        this.reporter = reporter;
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
        return inputStreamOrNullFor(approvedFor(testname));
    }

    @Override
    public InputStream inputOrNullForActual(String testname) throws IOException {
        return inputStreamOrNullFor(actualFor(testname));
    }

    @Override
    public void removeActual(String testname) throws IOException {
        actualFor(testname).delete(); // best efforts
    }

    @Override
    public File approvedFor(String testname) {
        return fileFor(approvedDir, testname, approvedExtension());
    }

    protected String approvedExtension() {
        return ".approved" + typeExtension();
    }

    @Override
    public File actualFor(String testname) {
        return fileFor(actualDir, testname, actualExtension());
    }

    @Override
    public void reportFailure(String testName, AssertionError e) {
        reporter.reportFailure(actualFor(testName), approvedFor(testName), e);
    }

    public FileSystemSourceOfApproval withActualDirectory(File actualDirectory) {
        this.actualDir = actualDirectory;
        return this;
    }

    public FileSystemSourceOfApproval withTypeExtension(String typeExtension) {
        this.typeExtension = typeExtension;
        return this;
    }

    protected String actualExtension() {
        return ".actual" + typeExtension();
    }

    protected String typeExtension() {
        return typeExtension;
    }

    private File fileFor(File dir, String testname, String suffix) {
        return new File(dir, testname + suffix);
    }

    private InputStream inputStreamOrNullFor(File file) throws FileNotFoundException {
        return !(file.exists() && file.isFile()) ?
                null : new BufferedInputStream(new FileInputStream(file));
    }

}
