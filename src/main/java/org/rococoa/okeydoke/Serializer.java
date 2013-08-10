package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer<T> {
    public void writeTo(T object, OutputStream os) throws IOException;
    public T readFrom(InputStream is) throws IOException;
    public T emptyThing();

}
