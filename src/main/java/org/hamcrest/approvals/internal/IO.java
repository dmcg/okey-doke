package org.hamcrest.approvals.internal;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IO {

    public static void writeBytes(File file, byte[] bytes) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            raf.setLength(0);
            raf.write(bytes);
        } finally {
            closeQuietly(raf);
        }
    }

    public static byte[] readBytes(File approvalFile) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(approvalFile, "r");
            byte[] result = new byte[(int) raf.length()];
            raf.readFully(result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(raf);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException ignored) {
        }
    }
}
