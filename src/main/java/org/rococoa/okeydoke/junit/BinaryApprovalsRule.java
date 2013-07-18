package org.rococoa.okeydoke.junit;

import org.rococoa.okeydoke.BaseApproverFactory;
import org.rococoa.okeydoke.BinaryApprover;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.Sources;
import org.rococoa.okeydoke.sources.StreamingFileSystemSourceOfApproval;

import java.io.File;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class BinaryApprovalsRule extends BaseApprovalsRule<byte[], BinaryApprover> {

    public static BinaryApprovalsRule fileSystemRule(final String sourceRoot) {
        return new BinaryApprovalsRule(new BaseApproverFactory<byte[], BinaryApprover>() {
            @Override
            public BinaryApprover create(String testName, Class<?> testClass) {
                return new BinaryApprover(testName,
                        Sources.in(new File(sourceRoot), testClass.getPackage()),
                        Reporters.reporter());
            }
        });
    }

    public static BinaryApprovalsRule streamingFileSystemRule(final String sourceRoot) {
        return new BinaryApprovalsRule(new BaseApproverFactory<byte[], BinaryApprover>() {
            @Override
            public BinaryApprover create(String testName, Class<?> testClass) {
                return new BinaryApprover(testName,
                        new StreamingFileSystemSourceOfApproval(new File(sourceRoot), testClass.getPackage()),
                        Reporters.reporter());
            }
        });
    }

    public BinaryApprovalsRule(BaseApproverFactory<byte[], BinaryApprover> factory) {
        super(factory);
    }

}
