package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.internal.OperatingSystem;
import com.oneeyedmen.okeydoke.reporters.ClassPathReporter;
import com.oneeyedmen.okeydoke.reporters.CommandLineReporter;
import com.oneeyedmen.okeydoke.reporters.PopupReporter;

import java.io.File;

public abstract class Reporters {

    public static final String DIFFER_PROPERTY_NAME = "okeydoke.differ";
    public static final String POPUP_PROPERTY_NAME = "okeydoke.popup";

    public static Reporter<File> fileSystemReporter() {
        return popup() ? new PopupReporter(differ()) : new CommandLineReporter(differ());
    }

    public static Reporter<File> classPathReporter() {
        return new ClassPathReporter(differ());
    }

    private static boolean popup() {
        String propertyValue = System.getProperty(POPUP_PROPERTY_NAME, "false");
        return propertyValue.equals("") ? true : Boolean.valueOf(propertyValue);
    }

    private static String differ() {
        String override = System.getProperty(DIFFER_PROPERTY_NAME);
        return override != null ? override : differFor(OperatingSystem.current());
    }

    public static String differFor(OperatingSystem os) {
        switch (os) {
            case LINUX:
                return "bcompare";
            case MAC:
                return "opendiff";
            default:
                return "diff";
        }
    }

}
