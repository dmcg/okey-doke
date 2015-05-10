package com.oneeyedmen.okeydoke.reporters;

import com.oneeyedmen.okeydoke.Reporter;

import java.io.File;

public class CommandLineReporter implements Reporter<File> {

    private final String differ;

    public CommandLineReporter(String differ) {
        this.differ = differ;
    }

    @Override
    public void reportFailure(File actual, File approved, Throwable e) {
        reportFailure(actual.getAbsolutePath(), approved.getAbsolutePath());
    }

    protected void reportFailure(String actualPath, String approvedPath) {
        System.err.println("To see differences...");
        System.err.println(diffCommandFor(actualPath, approvedPath));
        System.err.println("To approve...");
        System.err.format("cp '%s' '%s'\n", actualPath, approvedPath);
    }

    protected String diffCommandFor(String actualPath, String approvedPath) {
        return differ() + " '" + actualPath + "' '" + approvedPath + "'";
    }

    protected String differ() {
        return differ;
    }
}
