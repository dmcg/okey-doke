package com.oneeyedmen.okeydoke.internal;

import com.oneeyedmen.okeydoke.Resource;
import com.oneeyedmen.okeydoke.Serializer;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IO {

    public static <T> T readResource(Resource resource, Serializer<T> serializer) throws IOException {
        if (!resource.exists())
            return null;
        else {
            InputStream input = resource.inputStream();
            try {
                return serializer.readFrom(input);
            } finally {
                closeQuietly(input);
            }
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
