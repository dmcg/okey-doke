package org.rococoa.okeydoke;

import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class StringFormatter implements Formatter<String> {

    @Override
    public String readFrom(InputStream is) throws IOException {
        byte[] buf = new byte[is.available()]; // TODO - suss
        is.read(buf);
        return new String(buf);
    }

    @Override
    public void assertEquals(String expected, String actual) {
        Assert.assertEquals(expected, actual);
    }

    @Override
    public String formatted(Object actual) {
        if (actual.getClass().isArray())
            return stringFor((Object[]) actual);
        if (actual instanceof Iterable)
            return stringFor((Iterable) actual);
        return String.valueOf(actual);
    }

    @Override
    public void writeTo(String s, OutputStream os) throws IOException {
        os.write(s.getBytes());
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
