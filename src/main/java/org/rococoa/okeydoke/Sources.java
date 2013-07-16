package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.OperatingSystem;
import org.rococoa.okeydoke.reporters.CommandLineReporter;
import org.rococoa.okeydoke.reporters.PopupReporter;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;

import java.io.File;

public class Sources {

    public static final String DIFFER_PROPERTY_NAME = "okeydoke.differ";
    public static final String POPUP_PROPERTY_NAME = "okeydoke.popup";

    public static SourceOfApproval<File> in(String directory) {
        return in(new File(directory));
    }

    private static SourceOfApproval<File> in(File directory) {
        return new FileSystemSourceOfApproval(directory, directory, reporter());
    }

    public static SourceOfApproval<File> in(String srcRoot, Package thePackage, String actualDir) {
        return in(new File(srcRoot), thePackage, new File(actualDir));
    }

    public static SourceOfApproval<File> in(File srcRoot, Package thePackage, File actualDir) {
        return new FileSystemSourceOfApproval(
                    FileSystemSourceOfApproval.dirForPackage(srcRoot, thePackage),
                    actualDir,
                    reporter());
    }

    private static Reporter<File> reporter() {
        return popup() ? new PopupReporter(differ()) : new CommandLineReporter(differ());
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
