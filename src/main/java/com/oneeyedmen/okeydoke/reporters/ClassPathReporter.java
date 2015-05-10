package com.oneeyedmen.okeydoke.reporters;

import java.io.File;

public class ClassPathReporter extends CommandLineReporter {

    public ClassPathReporter(String differ) {
        super(differ);
    }

    @Override
    public void reportFailure(File actual, File approved, Throwable e) {
        reportFailure(actual.getAbsolutePath(), "<classpath location of " + approved.getName() + ">");
    }
}
