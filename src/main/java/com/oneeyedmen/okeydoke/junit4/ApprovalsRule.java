package com.oneeyedmen.okeydoke.junit4;

import com.oneeyedmen.okeydoke.Approver;
import com.oneeyedmen.okeydoke.ApproverFactories;
import com.oneeyedmen.okeydoke.ApproverFactory;
import com.oneeyedmen.okeydoke.Transcript;

import java.io.File;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class ApprovalsRule extends BaseApprovalsRule<Object, String, Approver> {

    public static final String usualJavaSourceRoot = "src/test/java";

    public static ApprovalsRule usualRule() {
        return fileSystemRule(usualJavaSourceRoot);
    }

    public static ApprovalsRule fileSystemRule(String sourceRoot) {
        return new ApprovalsRule(ApproverFactories.fileSystemApproverFactory(new File(sourceRoot)));
    }

    public static ApprovalsRule streamingFileSystemRule(String sourceRoot) {
        return new ApprovalsRule(ApproverFactories.streamingApproverFactory(new File(sourceRoot)));
    }

    public ApprovalsRule(ApproverFactory factory) {
        super(factory);
    }

    public ApprovalsRule(ApproverFactory factory, TestNamer testNamer) {
        super(factory, testNamer);
    }

    public Transcript transcript() {
        return approver().transcript();
    }
}
