package com.oneeyedmen.okeydoke.internal;

import java.io.File;

public abstract class DirectoryFinder {

    public static File firstDirThatExists(File... files) {
        for (File file : files) {
            if (file.isDirectory())
                return file;
        }
        return null;
    }

    private static final File[] likelyDirectories = {
            new File("src/test/java"),
            new File("src/test/kotlin"),
            new File("src/test/scala")
    };

    public static File findARootDirectory() {
        File firstDirThatExists = DirectoryFinder.firstDirThatExists(likelyDirectories);
        if (firstDirThatExists == null) {
            throw new IllegalStateException("Couldn't find a source directory");
        }
        return firstDirThatExists;
    }
}
