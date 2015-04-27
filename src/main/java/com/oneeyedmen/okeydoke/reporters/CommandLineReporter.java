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
        System.err.println("To see differences...");
        System.err.println(diffCommandFor(actual, approved));
        System.err.println("To approve...");
        System.err.format("cp '%s' '%s'\n", actual, approved);
    }

    protected String diffCommandFor(File actual, File approved) {
        return differ() + " '" + actual + "' '" + approved + "'";
    }

    protected String differ() {
        return differ;
    }
}
