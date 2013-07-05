package org.rococoa.okeydoke;

import java.util.Arrays;

public class StringFormatter implements Formatter<String> {

    @Override
    public byte[] bytesFor(String object) {
        return object.getBytes();
    }

    @Override
    public String objectFor(byte[] bytes) {
        return new String(bytes);
    }

    @Override
    public String formatted(Object actual) {
        if (actual.getClass().isArray())
            return stringFor((Object[]) actual);
        if (actual instanceof Iterable)
            return stringFor((Iterable) actual);
        return String.valueOf(actual);
    }

    private String stringFor(Iterable iterable) {
        StringBuilder result = new StringBuilder("[");
        for (Object o : iterable) {
            result.append("\"").append(formatted(o)).append("\",");
        }
        result.deleteCharAt(result.length() - 1).append("]");
        return result.toString();
    }

    private String stringFor(Object[] iterable) {
        return stringFor(Arrays.asList(iterable));
    }
}
