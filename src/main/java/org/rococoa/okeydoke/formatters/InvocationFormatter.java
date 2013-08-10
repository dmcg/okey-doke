package org.rococoa.okeydoke.formatters;

import org.junit.Assert;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.Invocation;

import java.nio.charset.Charset;

public class InvocationFormatter implements Formatter<Object, String> {

    public static final String LIST_SEPARATOR = ", ";
    private static final int LIST_SEPARATOR_LENGTH = LIST_SEPARATOR.length();

    @Override
    public String formatted(Object actual) {
        if (!(actual instanceof Invocation))
            throw new IllegalArgumentException("TODO - extract BaseStringFormatter");
        Invocation invocation = (Invocation) actual;
        return format(invocation.arguments, invocation.result);
    }

    @Override
    public Object emptyThing() {
        return "";
    }

    @Override
    public void assertEquals(String expected, String actual) {
        Assert.assertEquals(expected, actual);
    }

    private String format(Object[] arguments, Object result) {
        StringBuilder myResult = new StringBuilder();
        myResult.append("[").append(formatArguments(arguments)).append("] -> ");
        myResult.append(String.valueOf(result));
        return myResult.toString();
    }

    protected String formatArguments(Object[] arguments) {
        StringBuilder result = new StringBuilder();
        for (Object argument : arguments) {
            result.append(String.valueOf(argument)).append(LIST_SEPARATOR);
        }
        return result.substring(0, result.length() - LIST_SEPARATOR_LENGTH);
    }
}
