package org.rococoa.okeydoke;

import java.io.File;

public class ApproverFactories {

    public static ApproverFactory fileSystemApprover(final File sourceRoot, final File outDir) {
        return new ApproverFactory() {
            @Override
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(testName, Sources.in(sourceRoot, testClass.getPackage(), outDir));
            }
        };
    }

    public static ApproverFactory streamingFileSystemApprover(final File sourceRoot, final File outDir) {
        return new ApproverFactory() {
            @Override
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(testName, Sources.in(sourceRoot, testClass.getPackage(), outDir));
            }
        };
    }

}
