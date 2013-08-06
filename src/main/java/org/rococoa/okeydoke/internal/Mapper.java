package org.rococoa.okeydoke.internal;

public interface Mapper<U, T> {
    public T map(U next);
}
