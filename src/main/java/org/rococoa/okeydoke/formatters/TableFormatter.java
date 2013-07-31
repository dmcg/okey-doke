package org.rococoa.okeydoke.formatters;

import org.rococoa.okeydoke.util.Tabulator;

import java.nio.charset.Charset;

public class TableFormatter {

    private static final Tabulator tabulator = new Tabulator();

    private static StringFormatter instance = formatter(Charset.forName("UTF-8"));
    private static StringFormatter withHeader = withHeader(Charset.forName("UTF-8"));

    public static StringFormatter instance() {
        return instance;
    }

    public static StringFormatter withHeader() {
        return withHeader;
    }

    public static StringFormatter formatter(final Charset charset) {
        return new StringFormatter(charset) {
            @Override
            protected String stringFor(Iterable iterable) {
                return tabulator.tableOf(iterable);
            }
        };
    }

    public static StringFormatter withHeader(final Charset charset) {
        return new StringFormatter(charset) {
            @Override
            protected String stringFor(Iterable iterable) {
                return new Tabulator().headedTableOf(iterable);
            }
        };
    }


}
