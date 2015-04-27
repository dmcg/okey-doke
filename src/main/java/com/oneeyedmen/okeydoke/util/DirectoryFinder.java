package com.oneeyedmen.okeydoke.util;

import java.io.File;

public abstract class DirectoryFinder {

    public static File firstDirThatExists(File... files) {
        for (File file : files) {
            if (file.isDirectory())
                return file;
        }
        return null;
    }
}
