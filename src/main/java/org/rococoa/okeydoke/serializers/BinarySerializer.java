package org.rococoa.okeydoke.serializers;

import org.rococoa.okeydoke.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This Formatter reads and writes raw bytes, but compares a hex dump.
 */
public class BinarySerializer implements Serializer<byte[]> {

    @Override
    public byte[] readFrom(InputStream is) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int read = 0;
        while ((read = is.read()) != -1) {
            result.write(read);
        }
        return result.toByteArray();
    }

    @Override
    public void writeTo(byte[] object, OutputStream os) throws IOException {
        os.write(object);
    }

    @Override
    public byte[] emptyThing() {
        return new byte[0];
    }
}
