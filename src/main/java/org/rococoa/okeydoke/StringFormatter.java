package org.rococoa.okeydoke;

import org.junit.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

public class StringFormatter implements Formatter<String> {

    private static final int BUFFER_SIZE = 4 * 1024;

    private final Charset charset;

    public StringFormatter(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String readFrom(InputStream is) throws IOException {
        return readFully(new InputStreamReader(is, charset));
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
        os.write(s.getBytes(charset));
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

    private static String readFully(Reader input) throws IOException {
        StringBuilder result = new StringBuilder();
        char[] buffer = new char[BUFFER_SIZE];
        int charsRead;
        while ((charsRead = input.read(buffer)) != -1) {
            result.append(buffer, 0, charsRead);
        }
        return result.toString();
    }
}
