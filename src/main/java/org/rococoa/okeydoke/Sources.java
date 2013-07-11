package org.rococoa.okeydoke;

import java.io.File;

public class Sources {

    public static FileSystemSourceOfApproval in(String directory) {
        return new FileSystemSourceOfApproval(new File(directory));
    }

    public static FileSystemSourceOfApproval in(String srcRoot, Object o, String actualDir) {
        return new FileSystemSourceOfApproval(
                new File(srcRoot), o.getClass().getPackage(),
                new File(actualDir));
    }
}
