package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Checker;
import com.oneeyedmen.okeydoke.Reporter;
import com.oneeyedmen.okeydoke.Serializer;
import com.oneeyedmen.okeydoke.SourceOfApproval;
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
    public OutputStream outputForActual(String testName) throws IOException {
        return createAndOpenOutputStreamFor(actualFor(testName));
    }

    protected InputStream inputOrNullForApproved(String testName) throws FileNotFoundException {
        return inputStreamOrNullFor(approvedFor(testName));
    }

    @Override
    public void removeActual(String testName) throws IOException {
        actualFor(testName).delete(); // best efforts
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

    @Override
    public <T> T readActual(String testName, Serializer<T> serializer) throws IOException {
        return read(actualFor(testName), serializer);
    }

    @Override
    public <T> void checkActualAgainstApproved(OutputStream outputStream, String testName, Serializer<T> serializer, Checker<T> checker) throws IOException {
        checker.assertEquals(approvedContent(testName, serializer), readActual(testName, serializer));
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

    private BufferedOutputStream createAndOpenOutputStreamFor(File file) throws FileNotFoundException {
        file.getParentFile().mkdirs();
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    private InputStream inputStreamOrNullFor(File file) throws FileNotFoundException {
        return !(file.exists() && file.isFile()) ? null : inputStreamFor(file);
    }

    private BufferedInputStream inputStreamFor(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    private <T> T approvedContent(String testName, Serializer<T> serializer) throws IOException {
        InputStream existing = inputOrNullForApproved(testName);
        if (existing != null)
            return readAndClose(existing, serializer);
        T empty = serializer.emptyThing();
        writeToApproved(testName, empty, serializer); // so that an empty file exists for diff tools to chew on
        return empty;
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
