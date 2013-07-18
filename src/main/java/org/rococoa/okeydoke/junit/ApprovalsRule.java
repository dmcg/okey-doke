package org.rococoa.okeydoke.junit;

import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.ApproverFactories;
import org.rococoa.okeydoke.ApproverFactory;
import org.rococoa.okeydoke.Transcript;

import java.io.File;
import java.io.IOException;

/**
 * Use as an @Rule to automate approvals in JUnit.
 */
public class ApprovalsRule extends BaseApprovalsRule<Object, Approver> {

    public static ApprovalsRule fileSystemRule(String sourceRoot) {
        return new ApprovalsRule(ApproverFactories.fileSystemApprover(new File(sourceRoot)));
    }

    public static ApprovalsRule streamingFileSystemRule(String sourceRoot) {
        return new ApprovalsRule(ApproverFactories.fileSystemApprover(new File(sourceRoot)));
    }

    public ApprovalsRule(ApproverFactory factory) {
        super(factory);
    }

    public Transcript transcript() throws IOException {
        return approver().transcript();
    }
}
