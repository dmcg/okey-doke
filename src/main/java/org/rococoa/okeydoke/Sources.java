package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.OperatingSystem;

import java.io.File;

public class Sources {

    public static FileSystemSourceOfApproval in(String directory) {
        return new FileSystemSourceOfApproval(new File(directory), new File(directory), differ());
    }

    public static FileSystemSourceOfApproval in(String srcRoot, Object o, String actualDir) {
        return new FileSystemSourceOfApproval(
                FileSystemSourceOfApproval.dirForPackage(new File(srcRoot), o.getClass().getPackage()),
                new File(actualDir),
                differ());
    }

    private static String differ() {
        String override = System.getProperty("okeydoke.differ");
        return override != null ? override : differFor(OperatingSystem.current());
    }

    private static String differFor(OperatingSystem os) {
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
