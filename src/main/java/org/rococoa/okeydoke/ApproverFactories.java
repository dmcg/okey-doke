package org.rococoa.okeydoke;

import org.rococoa.okeydoke.sources.StreamingFileSystemSourceOfApproval;

import java.io.File;

public class ApproverFactories {

    public static ApproverFactory fileSystemApprover(final File sourceRoot, final File outDir) {
        return new ApproverFactory() {
            @Override
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(testName,
                        Sources.in(sourceRoot, testClass.getPackage(), outDir),
                        Reporters.reporter());
            }
        };
    }

    public static ApproverFactory streamingFileSystemApprover(final File sourceRoot, final File outDir) {
        return new ApproverFactory() {
            @Override
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(testName,
                        new StreamingFileSystemSourceOfApproval(sourceRoot, testClass.getPackage(), outDir),
                        Reporters.reporter());
            }
        };
    }

}
