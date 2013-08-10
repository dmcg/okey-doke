package org.rococoa.okeydoke.formatters;

import org.rococoa.okeydoke.internal.MappedIterable;
import org.rococoa.okeydoke.internal.Mapper;
import org.rococoa.okeydoke.util.Tabulator;

import java.nio.charset.Charset;

public class TableFormatter extends StringFormatter {

    private static final Tabulator tabulator = new Tabulator();

    private static StringFormatter instance = new TableFormatter();

    public static StringFormatter instance() {
        return instance;
    }

    private String[] headersOrNull;
    private Mapper<?,?> mapperOrNull;

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
