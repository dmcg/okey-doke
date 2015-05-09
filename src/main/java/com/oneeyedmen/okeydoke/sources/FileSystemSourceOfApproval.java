package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Reporter;
import com.oneeyedmen.okeydoke.Serializer;
import com.oneeyedmen.okeydoke.SourceOfApproval;

import java.io.*;

import static com.oneeyedmen.okeydoke.internal.IO.closeQuietly;

public class FileSystemSourceOfApproval implements SourceOfApproval {

    private final Reporter<File> reporter;
    private final File approvedDir;
    private final File actualDir;
    private final String typeExtension;

    public FileSystemSourceOfApproval(File approvedDir, File actualDir, String typeExtension, Reporter<File> reporter) {
        this.approvedDir = approvedDir;
        this.actualDir = actualDir;
        this.typeExtension = typeExtension;
        this.reporter = reporter;
    }

    public FileSystemSourceOfApproval(File directory, File actualDir, Reporter<File> reporter) {
        this(directory, actualDir, "", reporter);
    }

    public FileSystemSourceOfApproval(File directory, Reporter<File> reporter) {
        this(directory, directory, reporter);
    }

    public FileSystemSourceOfApproval withActualDirectory(File actualDirectory) {
        return new FileSystemSourceOfApproval(approvedDir, actualDirectory, typeExtension, reporter);
    }

    public FileSystemSourceOfApproval withTypeExtension(String typeExtension) {
        return new FileSystemSourceOfApproval(approvedDir, actualDir, typeExtension, reporter);
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
    public void reportFailure(String testName, AssertionError e) {
        reporter.reportFailure(actualFor(testName), approvedFor(testName), e);
    }

    @Override
    public <T> void writeToApproved(String testName, T thing, Serializer<T> serializer) throws IOException {
        OutputStream output = createAndOpenOutputStreamFor(approvedFor(testName));
        try {
            serializer.writeTo(thing, output);
        } finally {
            closeQuietly(output);
        }
    }

    public File approvedFor(String testname) {
        return fileFor(approvedDir, testname, approvedExtension());
    }

    public File actualFor(String testname) {
        return fileFor(actualDir, testname, actualExtension());
    }

    protected String approvedExtension() {
        return ".approved" + typeExtension();
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

    private BufferedOutputStream createAndOpenOutputStreamFor(File file) throws FileNotFoundException {
        file.getParentFile().mkdirs();
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    private InputStream inputStreamOrNullFor(File file) throws FileNotFoundException {
        return !(file.exists() && file.isFile()) ?
                null : new BufferedInputStream(new FileInputStream(file));
    }

}
