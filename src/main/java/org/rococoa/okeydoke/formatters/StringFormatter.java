package org.rococoa.okeydoke.formatters;

import org.rococoa.okeydoke.Formatter;

import java.util.Arrays;

/**
 * The standard Formatter, formats Objects to Strings.
 */
public class StringFormatter implements Formatter<Object, String> {

    private static final int BUFFER_SIZE = 4 * 1024;
    private final String quoteChar;

    public StringFormatter(String quoteChar) {
        this.quoteChar = quoteChar;
    }

    @Override
    public String formatted(Object actual) {
        if (actual == null)
            return representationOfNull();
        if (actual.getClass().isArray())
            return stringFor((Object[]) actual);
        if (actual instanceof Iterable)
            return stringFor((Iterable) actual);
        return String.valueOf(actual);
    }

    protected String representationOfNull() {
        return "NULL";
    }

    protected String stringFor(Iterable iterable) {
        StringBuilder result = new StringBuilder("[");
        for (Object o : iterable) {
            result.append(quoteChar).append(formatted(o)).append(quoteChar + ",");
        }
        if (result.length() > 1)
            result.deleteCharAt(result.length() - 1);
        result.append("]");
        return result.toString();
    }

    protected String stringFor(Object[] iterable) {
        return stringFor(Arrays.asList(iterable));
    }

}
