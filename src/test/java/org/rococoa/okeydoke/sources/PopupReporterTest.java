package org.rococoa.okeydoke.sources;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.Sources;
import org.rococoa.okeydoke.internal.OperatingSystem;
import org.rococoa.okeydoke.junit.TestDirectoryRule;
import org.rococoa.okeydoke.reporters.PopupReporter;

import java.io.IOException;

public class PopupReporterTest {

    @Rule public final TestDirectoryRule testDirectory = new TestDirectoryRule();

    @Ignore("UnIgnore to try me out")
    @Test public void test() throws IOException {
        Approver approver = new Approver("testname",
                new FileSystemSourceOfApproval(
                    testDirectory.dir(),
                    testDirectory.dir(),
                    new PopupReporter(Sources.differFor(OperatingSystem.current()))));

        approver.approve("Now is the time for all good men to come to the aid of the party.");
        approver.assertApproved("Now isn't the time for all gods men to come to the aid of the party");
    }

}
