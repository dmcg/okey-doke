package org.rococoa.okeydoke;

import org.rococoa.okeydoke.formatters.BinaryFormatter;
import org.rococoa.okeydoke.formatters.InvocationFormatter;
import org.rococoa.okeydoke.formatters.StringFormatter;
import org.rococoa.okeydoke.formatters.TableFormatter;

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
