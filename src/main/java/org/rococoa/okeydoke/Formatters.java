package org.rococoa.okeydoke;

import org.rococoa.okeydoke.formatters.BinaryFormatter;
import org.rococoa.okeydoke.formatters.InvocationFormatter;
import org.rococoa.okeydoke.formatters.StringFormatter;

import java.nio.charset.Charset;

public class Formatters {

    private static final Formatter<Object, String> STRING_FORMATTER = new StringFormatter(Charset.forName("UTF-8"));
    private static final Formatter<byte[], byte[]> BINARY_FORMATTER = new BinaryFormatter();
    private static final Formatter<Object, String> INVOCATION_FORMATTER = new InvocationFormatter(Charset.forName("UTF-8"));

    public static Formatter<Object, String> stringFormatter() {
        return STRING_FORMATTER;
    }

    public static Formatter<byte[], byte[]> binaryFormatter() {
        return BINARY_FORMATTER;
    }

    public static Formatter<Object, String> invocationFormatter() {
        return INVOCATION_FORMATTER;
    }

}
