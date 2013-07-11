package org.rococoa.okeydoke.sources;

import org.rococoa.okeydoke.FileSystemSourceOfApproval;

import java.io.File;

public class MacFileSystemSourceOfApproval extends FileSystemSourceOfApproval {
    
    public MacFileSystemSourceOfApproval(File directory) {
        super(directory);
    }

    public MacFileSystemSourceOfApproval(File srcRoot, Package packege, File actualDir) {
        super(srcRoot, packege, actualDir);
    }

    public MacFileSystemSourceOfApproval(File approvedDir, File actualDir) {
        super(approvedDir, actualDir);
    }

    public MacFileSystemSourceOfApproval(File root, Package packege) {
        super(root, packege);
    }

    @Override
    public void reportFailure(String testname, Throwable e) {
        System.err.format("To see differences...\nopendiff '%s' '%s'\n", actualFileFor(testname), approvedFileFor(testname));
    }
}
