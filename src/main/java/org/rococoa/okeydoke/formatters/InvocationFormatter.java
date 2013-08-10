package org.rococoa.okeydoke.formatters;

import org.rococoa.okeydoke.Invocation;

import java.nio.charset.Charset;

public class InvocationFormatter extends StringFormatter {

    public static final String LIST_SEPARATOR = ", ";
    private static final int LIST_SEPARATOR_LENGTH = LIST_SEPARATOR.length();

    public InvocationFormatter() {
        this(Charset.forName("UTF-8"));
    }

    public InvocationFormatter(Charset charset) {
        super(charset);
    }

    @Override
    public String formatted(Object actual) {
        if (!(actual instanceof Invocation))
            throw new IllegalArgumentException("TODO - extract BaseStringFormatter");
        Invocation invocation = (Invocation) actual;
        return format(invocation.arguments, invocation.result);
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
