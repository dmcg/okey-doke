package org.rococoa.okeydoke;

import org.rococoa.okeydoke.formatters.BinaryFormatter;
import org.rococoa.okeydoke.formatters.InvocationFormatter;
import org.rococoa.okeydoke.formatters.StringFormatter;

import java.nio.charset.Charset;

public class Formatters {

    private static final Formatter<Object, String> string = new StringFormatter();
    private static final Formatter<byte[], byte[]> binary = new BinaryFormatter();
    private static final Formatter<Object, String> invocation = new InvocationFormatter();

    public static Formatter<Object, String> stringFormatter() {
        return string;
    }

    public static Formatter<byte[], byte[]> binaryFormatter() {
        return binary;
    }

    public static Formatter<Object, String> invocationFormatter() {
        return invocation;
    }

}
