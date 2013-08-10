package org.rococoa.okeydoke.serializers;

import org.rococoa.okeydoke.Serializer;

import java.io.*;
import java.nio.charset.Charset;

/**
 * The standard Formatter, formats Objects to Strings.
 */
public class StringSerializer implements Serializer<String> {

    private static final int BUFFER_SIZE = 4 * 1024;

    private final Charset charset;

    public StringSerializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String readFrom(InputStream is) throws IOException {
        return readFully(new InputStreamReader(is, charset));
    }

    @Override
    public void writeTo(String s, OutputStream os) throws IOException {
        os.write(s.getBytes(charset));
    }

    public Charset getCharset() {
        return charset;
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
