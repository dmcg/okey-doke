package com.oneeyedmen.okeydoke.formatters;

import com.oneeyedmen.okeydoke.internal.MappedIterable;
import com.oneeyedmen.okeydoke.internal.Mapper;
import com.oneeyedmen.okeydoke.util.Tabulator;

public class TableFormatter extends StringFormatter {

    // NB - I've tried making TableFormatter implement Formatter<Iterable, String> but then it can't cope with arrays.

    private static final Tabulator tabulator = new Tabulator();

    private String[] headersOrNull;
    private Mapper<?,?> mapperOrNull;

    public TableFormatter() {
        super("\"");
    }

    public TableFormatter withHeaders(String... headers) {
        this.headersOrNull = headers;
        return this;
    }

    public TableFormatter withMapper(Mapper<?,?> mapper) {
        this.mapperOrNull = mapper;
        return this;
    }

    @Override
    protected String stringFor(Iterable iterable) {
        return headersOrNull == null ?
                tabulator.tableOf(mappedIterable(iterable)) :
                tabulator.headedTableOf(mappedIterable(iterable), headersOrNull);
    }

    private Iterable mappedIterable(Iterable iterable) {
        return mapperOrNull == null ? iterable : MappedIterable.map(iterable, mapperOrNull);
    }
}
