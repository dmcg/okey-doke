package org.rococoa.okeydoke.internal;

public class MappedIterable<T,U> extends MappingIterable<T,U> {

    private final Mapper<U, T> mapper;

    public static <T,U> Iterable<T> map(Iterable<U> i, Mapper<U, T> mapper) {
        return new MappedIterable<T, U>(i, mapper);
    };

    public MappedIterable(Iterable<U> wrapped, Mapper<U,T> mapper) {
        super(wrapped);
        this.mapper = mapper;
    }

    protected T map(U next) {
        return mapper.map(next);
    }
}
