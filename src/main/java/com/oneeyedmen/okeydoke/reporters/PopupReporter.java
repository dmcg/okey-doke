package com.oneeyedmen.okeydoke.reporters;

import java.io.File;
import java.io.IOException;

public class PopupReporter extends CommandLineReporter {

    public PopupReporter(String differ) {
        super(differ);
    }

    @Override
    public void reportFailure(File actual, File approved, Throwable e) {
        super.reportFailure(actual, approved, e);
        try {
            Runtime.getRuntime().exec(new String[] {
                    differ(),
                    actual.getAbsolutePath(),
                    approved.getAbsolutePath() });
        } catch (IOException x) {
            System.err.println("Failed to run differ : " + x);
        }
    }
}
