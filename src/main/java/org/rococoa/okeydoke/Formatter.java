package org.rococoa.okeydoke;

/**
 * Converts Object's to, and byte[] to and from, a type that can be compared.
 *
 * @param <T> the type of the comparison.
 */
public interface Formatter<T> {

    T formatted(Object object);

    byte[] bytesFor(T object);

    T objectFor(byte[] bytes);

    void assertEquals(T expected, T actual);
}
