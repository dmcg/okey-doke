package org.rococoa.okeydoke;

import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;

import java.io.File;

public class Sources {

    public static SourceOfApproval<File> in(String directory) {
        return in(new File(directory));
    }

    public static SourceOfApproval<File> in(File directory) {
        return new FileSystemSourceOfApproval(directory);
    }

    public static SourceOfApproval<File> in(File srcRoot, Package thePackage) {
        return new FileSystemSourceOfApproval(srcRoot, thePackage);
    }

}
