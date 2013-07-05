package org.rococoa.okeydoke;

public interface Formatter<T> {

    T formatted(Object object);

    byte[] bytesFor(T object);

    T objectFor(byte[] bytes);
}
