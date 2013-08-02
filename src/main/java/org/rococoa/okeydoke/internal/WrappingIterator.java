package org.rococoa.okeydoke.internal;

import java.util.Iterator;

public abstract class WrappingIterator<T, U> implements Iterator<T> {

    protected final Iterator<U> wrapped;

    public WrappingIterator(Iterator<U> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean hasNext() {
        return wrapped.hasNext();
    }

    @Override
    public abstract T next();

    @Override
    public void remove() {
        wrapped.remove();;
    }
}
