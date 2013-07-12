package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.OperatingSystem;

import java.io.File;

public class Sources {

    public static FileSystemSourceOfApproval in(String directory) {
        return in(new File(directory));
    }

    private static FileSystemSourceOfApproval in(File directory) {
        return new FileSystemSourceOfApproval(directory, directory, differ());
    }

    public static FileSystemSourceOfApproval in(String srcRoot, Package thePackage, String actualDir) {
        return in(new File(srcRoot), thePackage, new File(actualDir));
    }

    public static FileSystemSourceOfApproval in(File srcRoot, Package thePackage, File actualDir) {
        return new FileSystemSourceOfApproval(
                FileSystemSourceOfApproval.dirForPackage(srcRoot, thePackage),
                actualDir,
                differ());
    }

    private static String differ() {
        String override = System.getProperty("okeydoke.differ");
        return override != null ? override : differFor(OperatingSystem.current());
    }

    public static String differFor(OperatingSystem os) {
        switch (os) {
            case LINUX:
                return "bcompare";
            case MAC:
                return "opendiff";
            default:
                return "diff";
        }
    }
}
