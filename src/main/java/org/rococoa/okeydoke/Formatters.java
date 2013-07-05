package org.rococoa.okeydoke;

public class Formatters {

    private static final Formatter<String> STRING_FORMATTER = new StringFormatter();
    private static final Formatter<byte[]> BINARY_FORMATTER = new BinaryFormatter();

    public static Formatter<String> stringFormatter() {
        return STRING_FORMATTER;
    }

    public static Formatter<byte[]> binaryFormatter() {
        return BINARY_FORMATTER;
    }
}
