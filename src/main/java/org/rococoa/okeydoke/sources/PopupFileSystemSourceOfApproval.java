package org.rococoa.okeydoke.sources;

import java.io.File;
import java.io.IOException;

class PopupFileSystemSourceOfApproval extends FileSystemSourceOfApproval {

    public PopupFileSystemSourceOfApproval(File directory) {
        super(directory);
    }

    public PopupFileSystemSourceOfApproval(File srcRoot, Package thePackage, File actualDir) {
        super(srcRoot, thePackage, actualDir);
    }

    public PopupFileSystemSourceOfApproval(File approvedDir, File actualDir) {
        super(approvedDir, actualDir);
    }

    public PopupFileSystemSourceOfApproval(File approvedDir, File actualDir, String differ) {
        super(approvedDir, actualDir, differ);
    }

    public PopupFileSystemSourceOfApproval(File root, Package thePackage) {
        super(root, thePackage);
    }

    @Override
    public void reportFailure(String testname, Throwable e) {
        try {
            Runtime.getRuntime().exec(new String[] {
                    differ(), actualFileFor(testname).getAbsolutePath(), approvedFileFor(testname).getAbsolutePath() });
        } catch (IOException x) {
            System.err.println("Failed to run differ : " + x);
            super.reportFailure(testname, e);
        }
    }
}
