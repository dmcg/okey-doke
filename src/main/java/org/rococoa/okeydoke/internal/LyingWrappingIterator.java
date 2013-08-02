package org.rococoa.okeydoke.internal;

import java.util.Iterator;

public abstract class LyingWrappingIterator<T> extends WrappingIterator<T,T> {

    protected int i = 0;

    public LyingWrappingIterator(Iterator<T> wrapped) {
        super(wrapped);
    }

    @Override
    public boolean hasNext() {
        return wrapped.hasNext() || hasNext(i);
    }

    protected abstract boolean hasNext(int i);

    @Override
    public T next() {
        try {
            return next(i);
        } finally {
            i++;
        }
    }

    protected abstract T next(int i);

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}