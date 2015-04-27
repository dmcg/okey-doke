package com.oneeyedmen.okeydoke.formatters;

import com.oneeyedmen.okeydoke.Formatter;
import com.oneeyedmen.okeydoke.Invocation;

public class InvocationFormatter implements Formatter<Invocation, String> {

    public static final String LIST_SEPARATOR = ", ";
    private static final int LIST_SEPARATOR_LENGTH = LIST_SEPARATOR.length();

    @Override
    public String formatted(Invocation actual) {
        return format(actual.arguments, actual.result);
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
