package com.oneeyedmen.okeydoke;

import java.io.IOException;

public class RuntimeIOException extends RuntimeException {

    public RuntimeIOException(IOException x) {
        super(x);
    }

}
