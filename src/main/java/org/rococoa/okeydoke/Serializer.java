package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer<C> {
    void writeTo(C object, OutputStream os) throws IOException;
    C readFrom(InputStream is) throws IOException;
}
