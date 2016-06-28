package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.sources.FileSystemSourceOfApproval;
import com.oneeyedmen.okeydoke.sources.StreamingFileSystemSourceOfApproval;

import java.io.File;

public class Sources {

    public static FileSystemSourceOfApproval in(File directory) {
        return new FileSystemSourceOfApproval(directory, Reporters.fileSystemReporter());
    }

    public static FileSystemSourceOfApproval in(File srcRoot, Package thePackage) {
        return in(dirForPackage(srcRoot, thePackage));
    }

    public static FileSystemSourceOfApproval streamingInto(File directory) {
        return new StreamingFileSystemSourceOfApproval(directory, Reporters.fileSystemReporter());
    }

    public static FileSystemSourceOfApproval streamingInto(File srcRoot, Package thePackage) {
        return streamingInto(dirForPackage(srcRoot, thePackage));
    }

    private static File dirForPackage(File root, Package aPackage) {
        return new File(root, pathForPackage(aPackage));
    }

    public static String pathForPackage(Package aPackage) {
        return aPackage.getName().replaceAll("\\.", "/");
    }


}
