package org.rococoa.okeydoke;

import java.io.File;

public class ApproverFactories {

    public static ApproverFactory fileSystemApprover(final File sourceRoot) {
        return new ApproverFactory() {
            @Override
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(testName,
                        Sources.in(sourceRoot, testClass.getPackage()),
                        Reporters.reporter());
            }
        };
    }

    public static ApproverFactory streamingFileSystemApprover(final File sourceRoot) {
        return new ApproverFactory() {
            @Override
            public Approver create(String testName, Class<?> testClass) {
                return new Approver(testName,
                        Sources.streamingInto(sourceRoot, testClass.getPackage()),
                        Reporters.reporter());
            }
        };
    }

}
