package org.rococoa.okeydoke.formatters;

import org.rococoa.okeydoke.util.Tabulator;

import java.nio.charset.Charset;

public class TableFormatter {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Tabulator tabulator = new Tabulator();

    private static StringFormatter instance = formatter(UTF_8);

    public static StringFormatter instance() {
        return instance;
    }

    public static StringFormatter formatter(final Charset charset) {
        return new StringFormatter(charset) {
            @Override
            protected String stringFor(Iterable iterable) {
                return tabulator.tableOf(iterable);
            }
        };
    }

    public static StringFormatter withHeader(final String... headers) {
        return withHeader(UTF_8, headers);
    }

    public static StringFormatter withHeader(final Charset charset, final String... headers) {
        return new StringFormatter(charset) {
            @Override
            protected String stringFor(Iterable iterable) {
                return new Tabulator().headedTableOf(iterable, headers);
            }
        };
    }


}
