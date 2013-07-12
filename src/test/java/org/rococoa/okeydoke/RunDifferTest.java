package org.rococoa.okeydoke;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.internal.OperatingSystem;
import org.rococoa.okeydoke.junit.TestDirectoryRule;

import java.io.File;
import java.io.IOException;

public class RunDifferTest {

    @Rule public final TestDirectoryRule testDirectory = new TestDirectoryRule();

    @Ignore("manual")
    @Test public void test() throws IOException {
        Approver approver = new Approver("testname", new PopupFileSystemSourceOfApproval(
                testDirectory.dir(), testDirectory.dir(), Sources.differFor(OperatingSystem.current())));

        approver.approve("Now is the time for all good men to come to the aid of the party.");
        approver.assertApproved("Now isn't the time for all gods men to come to the aid of the party");
    }

    private class PopupFileSystemSourceOfApproval extends FileSystemSourceOfApproval {
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
                Runtime.getRuntime().exec(diffCommandFor(actualFileFor(testname), approvedFileFor(testname)));
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
