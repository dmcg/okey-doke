package com.oneeyedmen.okeydoke.internal;

public interface Mapper<U, T> {
    public T map(U next);
}
