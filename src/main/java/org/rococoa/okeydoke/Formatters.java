package org.rococoa.okeydoke;

import java.nio.charset.Charset;

public class Formatters {

    private static final Formatter<Object, String> STRING_FORMATTER = new StringFormatter(Charset.forName("UTF-8"));
    private static final Formatter<byte[], byte[]> BINARY_FORMATTER = new BinaryFormatter();

    public static Formatter<Object, String> stringFormatter() {
        return STRING_FORMATTER;
    }

    public static Formatter<byte[], byte[]> binaryFormatter() {
        return BINARY_FORMATTER;
    }
}
