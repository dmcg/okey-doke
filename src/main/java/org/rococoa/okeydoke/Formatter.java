package org.rococoa.okeydoke;

public interface Formatter {
    String stringFor(Object actual);

    byte[] bytesFor(Object object);
}
