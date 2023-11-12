package com.oneeyedmen.okeydoke.junit4;

import com.oneeyedmen.okeydoke.util.TestDirectory;
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
