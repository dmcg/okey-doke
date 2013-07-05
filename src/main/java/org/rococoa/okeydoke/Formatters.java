package org.rococoa.okeydoke;

public class Formatters {

    private static final Formatter<String> STRING_FORMATTER = new StringFormatter();

    public static Formatter<String> stringFormatter() {
        return STRING_FORMATTER;
    }
}
