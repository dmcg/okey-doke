package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.OperatingSystem;
import org.rococoa.okeydoke.sources.FileSystemSourceOfApproval;
import org.rococoa.okeydoke.sources.PopupFileSystemSourceOfApproval;

import java.io.File;

public class Sources {

    public static final String DIFFER_PROPERTY_NAME = "okeydoke.differ";
    public static final String POPUP_PROPERTY_NAME = "okeydoke.popup";

    public static SourceOfApproval in(String directory) {
        return in(new File(directory));
    }

    private static SourceOfApproval in(File directory) {
        return popup() ?
            new PopupFileSystemSourceOfApproval(directory, directory, differFor(OperatingSystem.current())) :
            new FileSystemSourceOfApproval(directory, directory, differFor(OperatingSystem.current()));
    }

    public static SourceOfApproval in(String srcRoot, Package thePackage, String actualDir) {
        return in(new File(srcRoot), thePackage, new File(actualDir));
    }

    public static SourceOfApproval in(File srcRoot, Package thePackage, File actualDir) {
        return popup() ?
            new PopupFileSystemSourceOfApproval(
                    FileSystemSourceOfApproval.dirForPackage(srcRoot, thePackage),
                    actualDir,
                    differ()) :
            new FileSystemSourceOfApproval(
                    FileSystemSourceOfApproval.dirForPackage(srcRoot, thePackage),
                    actualDir,
                    differ());
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
