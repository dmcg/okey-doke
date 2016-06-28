package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.*;
import com.oneeyedmen.okeydoke.internal.IO;

import java.io.File;
import java.io.IOException;

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
    public Resource actualFor(String testName) throws IOException {
        return new FileResource(actualFileFor(testName));
    }

    @Override
    public Resource approvedFor(String testName) throws IOException {
        return new FileResource(approvedFileFor(testName));
    }

    @Override
    public void reportFailure(String testName, AssertionError e) {
        reporter.reportFailure(actualFileFor(testName), approvedFileFor(testName), e);
    }

    @Override
    public <T> void checkActualAgainstApproved(String testName, Serializer<T> serializer, Checker<T> checker) throws IOException {
        checker.assertEquals(
                IO.readResource(approvedFor(testName), serializer),
                IO.readResource(actualFor(testName), serializer));
    }

    public File approvedFileFor(String testName) {
        return fileFor(approvedDir, testName, approvedExtension());
    }

    public File actualFileFor(String testName) {
        return fileFor(actualDir, testName, actualExtension());
    }

    private String approvedExtension() {
        return ".approved" + typeExtension();
    }

    private String actualExtension() {
        return ".actual" + typeExtension();
    }

    private String typeExtension() {
        return typeExtension;
    }

    private File fileFor(File dir, String testName, String suffix) {
        return new File(dir, testName + suffix);
    }


}
