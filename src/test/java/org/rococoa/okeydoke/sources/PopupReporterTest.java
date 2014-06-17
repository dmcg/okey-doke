package org.rococoa.okeydoke.sources;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.Approver;
import org.rococoa.okeydoke.Reporters;
import org.rococoa.okeydoke.internal.OperatingSystem;
import org.rococoa.okeydoke.junit.TestDirectoryRule;
import org.rococoa.okeydoke.reporters.PopupReporter;

public class PopupReporterTest {

    @Rule public final TestDirectoryRule testDirectory = new TestDirectoryRule();

    @Ignore("UnIgnore to try me out")
    @Test public void test() {
        Approver approver = new Approver("testname",
                new FileSystemSourceOfApproval(testDirectory.dir()),
                    new PopupReporter(Reporters.differFor(OperatingSystem.current())));

        approver.makeApproved("Now is the time for all good men to come to the aid of the party.");
        approver.assertApproved("Now isn't the time for all gods men to come to the aid of the party");
    }

}
