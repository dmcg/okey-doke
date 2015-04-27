package com.oneeyedmen.okeydoke.junit;

import com.oneeyedmen.okeydoke.ApproverFactories;
import com.oneeyedmen.okeydoke.ApproverFactory;
import com.oneeyedmen.okeydoke.BinaryApprover;

import java.io.File;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class BinaryApprovalsRule extends BaseApprovalsRule<byte[], byte[], BinaryApprover> {

    public static BinaryApprovalsRule fileSystemRule(String sourceRoot) {
        return new BinaryApprovalsRule(ApproverFactories.binaryFileSystemApproverFactory(new File(sourceRoot)));
    }

    public static BinaryApprovalsRule fileSystemRule(String sourceRoot, String extension) {
        return new BinaryApprovalsRule(ApproverFactories.binaryFileSystemApproverFactory(new File(sourceRoot), extension));
    }

    public static BinaryApprovalsRule streamingFileSystemRule(final String sourceRoot) {
        return ApproverFactories.streamingBinaryApproverFactory(new File(sourceRoot));
    }

    public BinaryApprovalsRule(ApproverFactory<BinaryApprover> factory) {
        super(factory);
    }

}
