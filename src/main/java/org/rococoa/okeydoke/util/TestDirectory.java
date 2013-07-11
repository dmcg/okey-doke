package org.rococoa.okeydoke.util;

import java.io.*;

/**
 * Maintains a directory which is created empty every time.
 */
public class TestDirectory extends File {
    
    public final static File BASE_DIR;

    static {
        String tmpdir = System.getProperty("java.io.tmpdir");
        tmpdir = tmpdir == null ? "C:/temp" : tmpdir;
        try {
            BASE_DIR = new File(tmpdir).getCanonicalFile();
                // otherwise is short form on Windows
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TestDirectory empty(Object test) {
        return new TestDirectory(test);
    }

    public TestDirectory(String relativePath) {
        super(fileFor(relativePath).toString());
        if (this.isFile())
            throw new RuntimeException(this + " is a file not a directory");
        try {
            deleteWithRetry(this, 5);
        } catch (IOException e) {
            throw new RuntimeException(e);
            // don't throw IOException as it's too inconvenient in test initializers
        }
        mkdirs();
    }

    public TestDirectory(Class<?> testClass) {
        this(testClass.getName());
    }

    public TestDirectory(Object test) {
        this(test.toString());
    }

    public static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        if (!f.delete())
            throw new IOException("Failed to delete file: " + f);
    }

    public File createFileFrom(String filename, String contents) throws IOException {
        File result = file(filename);
        result.getParentFile().mkdirs();
        BufferedWriter w = new BufferedWriter(new FileWriter(result));
        w.write(contents);
        w.close();
        return result;
    }

    public File touchFile(String filename) throws IOException {
        return touchFile(filename, 0);
    }

    public File touchFile(String filename, long size) throws IOException {
        File result = file(filename);
        result.getParentFile().mkdirs();
        RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(result, "rw");
        randomAccessFile.setLength(size);
        randomAccessFile.close();
        return result;
    }
    
    public File mkDirs(String path) throws IOException {
        File result = file(path);
        result.mkdirs();
        if (!result.isDirectory())
             throw new IOException("Could not create dir " + path);
        return result;
    }
    
    public boolean hasFile(String filename) {
        return file(filename).isFile();
    }
    
    public File file(String filename) {
        return new File(this, filename);
    }

    public void remove() throws IOException {
        deleteWithRetry(this, 5);
    }

    /**
     * Some parts of the runtime have a nasty habit of keeping files open a little longer
     * than they should. Retry the delete, gc'ing to flush the close on finalize
     * out of the woodwork.
     */
    private void deleteWithRetry(File file, int retryCount) throws IOException {
        if (!file.exists())
            return;
        for (int i = 0; i < retryCount - 1 && !file.exists(); i++) {
            try {
                delete(file);
            } catch (IOException ignoredForNow) {}
            System.gc();
        }
        
        delete(file);
    }

    static File fileFor(String relativePath) {
        return new File(BASE_DIR, relativePath);
    }
}
