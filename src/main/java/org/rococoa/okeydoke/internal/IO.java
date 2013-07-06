package org.rococoa.okeydoke.internal;

import java.io.Closeable;
import java.io.IOException;

public class IO {

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
