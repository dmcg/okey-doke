package org.rococoa.okeydoke.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.util.TestDirectory;

public class TestDirectoryRule extends TestWatcher {

    private TestDirectory testDirectory;

    @Override
    protected void starting(Description description) {
        testDirectory = new TestDirectory(description.getDisplayName());
    }

    public TestDirectory dir() {
        return testDirectory;
    }
}
