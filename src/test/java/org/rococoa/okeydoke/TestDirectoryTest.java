/**
 * 
 */
package org.rococoa.okeydoke;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestDirectoryTest {
    
    private final String dirname = "testDir";

    @Before public void setUp() throws IOException {
        new TestDirectory(dirname).delete();
    }

    @Test public void testCreate() {
        assertFalse(TestDirectory.fileFor(dirname).exists());
        TestDirectory dir = new TestDirectory(dirname);
        assertTrue(TestDirectory.fileFor(dirname).exists());
        assertTrue(dir.exists());
    }

    @Test public void testRemove() throws IOException {
        TestDirectory dir = new TestDirectory(dirname);
        assertTrue(dir.exists());
        File file = new File(dir, "sub/sub/sub/file");
        assertFalse(file.exists());
        createFile(file);
        assertTrue(file.exists());
        dir.remove();
        assertFalse(file.exists());
        assertFalse(dir.exists());
    }

    @Test public void testEmptyOnCreate() throws IOException {
        TestDirectory dir = new TestDirectory(dirname);
        File file = new File(dir, "sub/sub/sub/file");
        createFile(file);
        dir = new TestDirectory(dirname);
        assertTrue(dir.exists());
        assertFalse(file.exists());
    }

    @Test public void testCreateFileFrom() throws IOException {
        TestDirectory dir = new TestDirectory(dirname);
        File file = dir.createFileFrom("temp.txt", "Now is the time etc");

        BufferedReader r = new BufferedReader(new FileReader(file));
        assertEquals("Now is the time etc", r.readLine());
        r.close();
    }

    private void createFile(File file) throws IOException {
        new File(file.getParent()).mkdirs();
        file.createNewFile();
    }

}