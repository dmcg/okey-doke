package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Formats things of type T to things of type C (for comparison), that can be assertEquals'd,
 * and reads and writes Os from streams.
 *
 * Note that as the format conversion is only applied one way, it does not have to
 * be reversible - so don't worry too much about escaping etc.
 *
 * @param <C> the type of the comparison.
 */
public interface Formatter<T, C> {

    C formatted(T object);

    void writeTo(C object, OutputStream os) throws IOException;
    C readFrom(InputStream is) throws IOException;

    void assertEquals(C expected, C actual);
}
