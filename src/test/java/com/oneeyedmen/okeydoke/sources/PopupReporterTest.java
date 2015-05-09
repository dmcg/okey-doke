package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Approver;
import com.oneeyedmen.okeydoke.Reporters;
import com.oneeyedmen.okeydoke.internal.OperatingSystem;
import com.oneeyedmen.okeydoke.junit.TestDirectoryRule;
import com.oneeyedmen.okeydoke.reporters.PopupReporter;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class PopupReporterTest {

    @Rule public final TestDirectoryRule testDirectory = new TestDirectoryRule();

    private PopupReporter reporter = new PopupReporter(Reporters.differFor(OperatingSystem.current()));

    @Ignore("UnIgnore to try me out")
    @Test public void test() throws IOException {
        Approver approver = new Approver("testname",
                new FileSystemSourceOfApproval(testDirectory.dir(), reporter)
        );

        approver.makeApproved("Now is the time for all good men to come to the aid of the party.");
        approver.assertApproved("Now isn't the time for all gods men to come to the aid of the party");
    }

}
