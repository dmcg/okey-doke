package org.rococoa.okeydoke.util;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class TabulatorTest {

    private final Tabulator tabulator = new Tabulator();

    @Test public void empty_when_empty_collection() {
        Collection<? extends Collection<?>> empty = Collections.emptyList();
        assertEquals("", tabulator.tableOf(empty));
    }

    @Test public void empty_when_first_row_empty() {
        Collection<?> emptyRow = Collections.emptyList();
        assertEquals("", tabulator.tableOf(asList(emptyRow)));
    }

    @Test public void no_header_row() {
        Collection<? extends Collection<?>> data = asList(
                asList("one", "two", "three"),
                asList("four", "five", "siiiiiiix"));

        assertEquals(
"|one |two |three    |\n" +
"|four|five|siiiiiiix|\n",
            tabulator.tableOf(data));
    }

    @Test public void header_row() {
        Collection<? extends Collection<?>> data = asList(
                asList("Header 1", "Header 2", "Header 3"),
                asList("one", "two", "three"),
                asList("four", "five", "siiiiiiix"));

        assertEquals(
"|Header 1|Header 2|Header 3 |\n" +
"|--------|--------|---------|\n" +
"|one     |two     |three    |\n" +
"|four    |five    |siiiiiiix|\n",
                tabulator.headedTableOf(data));
    }

    @Test public void header_row_for_one_row() {
        Collection<? extends Collection<?>> data = asList(
                asList("Header 1", "Header 2", "Header 3"));

        assertEquals(
"|Header 1|Header 2|Header 3|\n" +
"|--------|--------|--------|\n",
                tabulator.headedTableOf(data));
    }

}
