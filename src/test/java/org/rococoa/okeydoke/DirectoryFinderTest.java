package org.rococoa.okeydoke;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class DirectoryFinderTest {


    public static final File ROOT = new File("target/dirfinder");

    @Rule public CleanDirectoryRule cleanDir = new CleanDirectoryRule(ROOT);

    private static final File not_there = new File(ROOT, "not_there");
    private static final File thereButAFile = new File(ROOT, "is_a_file");
    private static final File there = new File(ROOT, "is_a_dir");

    @Before
    public void setup() throws IOException {
        thereButAFile.createNewFile();
        there.mkdirs();
        not_there.delete();

        assertFalse(not_there.exists());
        assertTrue(thereButAFile.isFile());
        assertTrue(there.isDirectory());
    }

    @Test public void first_existing_dir() throws IOException {
        assertEquals(there, DirectoryFinder.firstDirThatExists(not_there, thereButAFile, there));

    }

    @Test public void returns_null_if_none_found() throws IOException {
        assertNull(DirectoryFinder.firstDirThatExists(not_there, thereButAFile));
    }

}
