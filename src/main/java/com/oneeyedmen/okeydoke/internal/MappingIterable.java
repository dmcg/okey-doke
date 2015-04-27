package com.oneeyedmen.okeydoke.internal;

import java.util.Iterator;

public abstract class MappingIterable<T,U> implements Iterable<T> {

    private final Iterable<U> wrapped;

    public MappingIterable(Iterable<U> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Iterator<T> iterator() {
        return new WrappingIterator<T,U>(wrapped.iterator()) {
            @Override
            public T next() {
                return map(wrapped.next());
            }
        };
    }

    protected abstract T map(U next);
}
