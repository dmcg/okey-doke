package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.formatters.BinaryFormatter;
import com.oneeyedmen.okeydoke.formatters.InvocationFormatter;
import com.oneeyedmen.okeydoke.formatters.StringFormatter;
import com.oneeyedmen.okeydoke.formatters.TableFormatter;

public class Formatters {

    private static final Formatter<Object, String> doubleQuotedString = new StringFormatter("\"");
    private static final Formatter<byte[], byte[]> binary = new BinaryFormatter();
    private static final Formatter<Object, String> table = new TableFormatter();
    private static final Formatter<Invocation, String> invocation = new InvocationFormatter();


    public static Formatter<Object, String> stringFormatter() {
        return doubleQuotedString;
    }

    public static Formatter<byte[], byte[]> binaryFormatter() {
        return binary;
    }

    public static Formatter<Invocation, String> invocationFormatter() {
        return invocation;
    }

    public static Formatter<Object, String> table() {
        // declared as base type to stop mutation with TableFormatter.withXXX methods
        return table;
    }
}
