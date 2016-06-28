package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.*;
import com.oneeyedmen.okeydoke.internal.IO;

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

    public FileSystemSourceOfApproval(File approvedDir, File actualDir, Reporter<File> reporter) {
        this(approvedDir, actualDir, "", reporter);
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
    public Resource resourceFor(String testName) throws IOException {
        return new FileResource(actualFor(testName));
    }

    protected InputStream inputOrNullForApproved(String testName) throws FileNotFoundException {
        return inputStreamOrNullFor(approvedFor(testName));
    }

    @Override
    public void removeActual(String testName) throws IOException {
        File file = actualFor(testName);
        if (file.isFile() && !file.delete())
            throw new IOException("Couldn't delete file " + file);
    }

    @Override
    public void reportFailure(String testName, AssertionError e) {
        reporter.reportFailure(actualFor(testName), approvedFor(testName), e);
    }

    @Override
    public <T> void writeToApproved(String testName, T thing, Serializer<T> serializer) throws IOException {
        OutputStream output = outputStreamFor(approvedFor(testName));
        try {
            serializer.writeTo(thing, output);
        } finally {
            closeQuietly(output);
        }
    }

    @Override
    public <T> T actualContentOrNull(String testName, Serializer<T> serializer) throws IOException {
        File file = actualFor(testName);
        return file.isFile() ? read(file, serializer) : null;
    }

    @Override
    public <T> void checkActualAgainstApproved(OutputStream outputStream, String testName, Serializer<T> serializer, Checker<T> checker) throws IOException {
        checker.assertEquals(approvedContentOrNull(testName, serializer), actualContentOrNull(testName, serializer));
    }

    @Override
    public void removeApproved(String testName) throws IOException {
        File file = approvedFor(testName);
        file.delete();
        if (file.exists())
            throw new IOException("Couldn't delete file " + file);
    }

    public File approvedFor(String testName) {
        return fileFor(approvedDir, testName, approvedExtension());
    }

    public File actualFor(String testName) {
        return fileFor(actualDir, testName, actualExtension());
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

    private File fileFor(File dir, String testName, String suffix) {
        return new File(dir, testName + suffix);
    }

    private OutputStream outputStreamFor(final File file) throws FileNotFoundException {
        return new LazyOutputStream() {
            @Override
            protected OutputStream createOut() throws IOException {
                file.getParentFile().mkdirs();
                return new BufferedOutputStream(new FileOutputStream(file));
            }
        };
    }

    private InputStream inputStreamOrNullFor(File file) throws FileNotFoundException {
        return !(file.exists() && file.isFile()) ? null : inputStreamFor(file);
    }

    private BufferedInputStream inputStreamFor(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    private <T> T approvedContentOrNull(String testName, Serializer<T> serializer) throws IOException {
        InputStream existing = inputOrNullForApproved(testName);
        return (existing != null) ? readAndClose(existing, serializer) : null;
    }

    private <T> T read(File file, Serializer<T> serializer) throws IOException {
        return readAndClose(inputStreamFor(file), serializer);
    }

    private <T> T readAndClose(InputStream input, Serializer<T> serializer) throws IOException {
        try {
            return serializer.readFrom(input);
        } finally {
            IO.closeQuietly(input);
        }
    }


}
