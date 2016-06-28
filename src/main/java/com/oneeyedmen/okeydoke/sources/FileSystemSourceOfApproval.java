package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.*;
import com.oneeyedmen.okeydoke.internal.IO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    public Resource actualResourceFor(String testName) throws IOException {
        return new FileResource(actualFor(testName));
    }

    @Override
    public Resource approvedResourceFor(String testName) throws IOException {
        return new FileResource(approvedFor(testName));
    }

    @Override
    public void reportFailure(String testName, AssertionError e) {
        reporter.reportFailure(actualFor(testName), approvedFor(testName), e);
    }

    @Override
    public <T> void checkActualAgainstApproved(OutputStream outputStream, String testName, Serializer<T> serializer, Checker<T> checker) throws IOException {
        checker.assertEquals(
                readResource(approvedResourceFor(testName), serializer),
                readResource(actualResourceFor(testName), serializer));
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

    private <T> T readResource(Resource resource, Serializer<T> serializer) throws IOException {
        if (!resource.exists())
            return null;
        else
            return readAndClose(resource.inputStream(), serializer);
    }

    private <T> T readAndClose(InputStream input, Serializer<T> serializer) throws IOException {
        try {
            return serializer.readFrom(input);
        } finally {
            IO.closeQuietly(input);
        }
    }
}
