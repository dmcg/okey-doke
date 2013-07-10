package org.rococoa.okeydoke;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

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
