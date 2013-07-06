package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Converts Object's to, and byte[] to and from, T - a type that can be compared.
 *
 * Note that as the format conversion is only applied one way, it does not have to
 * be reversible - so don't worry too much about escaping etc.
 *
 * @param <T> the type of the comparison.
 */
public interface Formatter<T> {

    T formatted(Object object);

    void writeTo(T object, OutputStream os) throws IOException;
    T readFrom(InputStream is) throws IOException;

    void assertEquals(T expected, T actual);
}
