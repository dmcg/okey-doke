package org.rococoa.okeydoke;

import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;
import org.rococoa.okeydoke.sources.StreamingFileSystemSourceOfApproval;

import java.io.File;

public class Sources {

    public static SourceOfApproval<File> in(File directory) {
        return new FileSystemSourceOfApproval(directory);
    }

    public static SourceOfApproval<File> in(File srcRoot, Package thePackage) {
        return in(dirForPackage(srcRoot, thePackage));
    }

    public static SourceOfApproval<File> streamingInto(File directory) {
        return new StreamingFileSystemSourceOfApproval(directory);
    }

    public static SourceOfApproval<File> streamingInto(File srcRoot, Package thePackage) {
        return streamingInto(dirForPackage(srcRoot, thePackage));
    }

    public static File dirForPackage(File root, Package aPackage) {
        return new File(root, aPackage.getName().replaceAll("\\.", "/"));
    }
}
