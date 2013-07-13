package org.rococoa.okeydoke.sources;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.Sources;
import org.rococoa.okeydoke.internal.OperatingSystem;
import org.rococoa.okeydoke.junit.TestDirectoryRule;

import java.io.IOException;

public class PopupFileSystemSourceOfApprovalTest {

    @Rule public final TestDirectoryRule testDirectory = new TestDirectoryRule();

    @Ignore("UnIgnore to try me out")
    @Test public void test() throws IOException {
        Approver approver = new Approver("testname",
                new PopupFileSystemSourceOfApproval(
                    testDirectory.dir(),
                    testDirectory.dir(),
                    Sources.differFor(OperatingSystem.current())));

        approver.approve("Now is the time for all good men to come to the aid of the party.");
        approver.assertApproved("Now isn't the time for all gods men to come to the aid of the party");
    }

}
