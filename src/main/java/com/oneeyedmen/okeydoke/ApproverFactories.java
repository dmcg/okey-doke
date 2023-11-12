package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.junit4.BinaryApprovalsRule;

import java.io.File;

public class ApproverFactories {

    public static ApproverFactory<Approver> fileSystemApproverFactory(final File sourceRoot) {
        return new ApproverFactory<Approver>() {
            @Override
            public Approver createApprover(String testName, Class<?> testClass) {
                return new Approver(testName,
                        Sources.in(sourceRoot, testClass.getPackage())
                );
            }
        };
    }

    public static ApproverFactory<Approver> fileSystemApproverFactory(final File sourceRoot, final String extension) {
        return new ApproverFactory<Approver>() {
            @Override
            public Approver createApprover(String testName, Class<?> testClass) {
                return new Approver(testName,
                        Sources.in(sourceRoot, testClass.getPackage()).withTypeExtension(extension)
                );
            }
        };
    }

    public static ApproverFactory<Approver> streamingApproverFactory(final File sourceRoot) {
        return new ApproverFactory<Approver>() {
            @Override
            public Approver createApprover(String testName, Class<?> testClass) {
                return new Approver(testName,
                        Sources.streamingInto(sourceRoot, testClass.getPackage())
                );
            }
        };
    }

    public static ApproverFactory<BinaryApprover> binaryFileSystemApproverFactory(final File sourceRoot) {
        return new ApproverFactory<BinaryApprover>() {
            @Override
            public BinaryApprover createApprover(String testName, Class<?> testClass) {
                return new BinaryApprover(testName,
                        Sources.in(sourceRoot, testClass.getPackage())
                );
            }
        };
    }

    public static BinaryApprovalsRule streamingBinaryApproverFactory(final File sourceRoot) {
        return new BinaryApprovalsRule(new ApproverFactory<BinaryApprover>() {
            @Override
            public BinaryApprover createApprover(String testName, Class<?> testClass) {
                return new BinaryApprover(testName,
                        Sources.streamingInto(sourceRoot, testClass.getPackage())
                );
            }
        });
    }

    public static ApproverFactory<BinaryApprover> binaryFileSystemApproverFactory(final File sourceRoot, final String extension) {
        return new ApproverFactory<BinaryApprover>() {
            @Override
            public BinaryApprover createApprover(String testName, Class<?> testClass) {
                return new BinaryApprover(testName,
                        Sources.in(sourceRoot, testClass.getPackage()).withTypeExtension(extension)
                );
            }
        };
    }
}
