package org.rococoa.okeydoke.formatters;

import org.rococoa.okeydoke.InvocationFormatter;

public class DefaultInvocationFormatter implements InvocationFormatter {

    public static final String LIST_SEPARATOR = ", ";
    private static final int LIST_SEPARATOR_LENGTH = LIST_SEPARATOR.length();

    @Override
    public String format(Object[] arguments, Object result) {
        StringBuilder myResult = new StringBuilder();
        myResult.append("[").append(formatArguments(arguments)).append("] -> ");
        myResult.append(String.valueOf(result)).append("\n");
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
