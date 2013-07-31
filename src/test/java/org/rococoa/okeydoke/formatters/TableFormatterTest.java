package org.rococoa.okeydoke.formatters;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;

import static java.util.Arrays.asList;

public class TableFormatterTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void array_is_listed_by_row() throws IOException {
        approver.assertApproved(new String[] {"one", "two", "three"}, TableFormatter.instance());
    }

    @Test public void array_table_is_laid_out() throws IOException {
        String [][] data = {
                {"one", "two", "three"},
                {"four", "five", "siiiiiiix"}};
        approver.assertApproved(data, TableFormatter.instance());
    }

    @Test public void iterable_is_listed_by_row() throws IOException {
        approver.assertApproved(asList("one", "two", "three"), TableFormatter.instance());
    }

    @Test public void iterable_table_is_laid_out() throws IOException {
        Iterable<?> data = asList(
                asList("one", "two", "three"),
                asList("four", "five", "siiiiiiix"));
        approver.assertApproved(data, TableFormatter.instance());
    }

    @Test public void null_is_printed() throws IOException {
        approver.assertApproved(null);
    }

    @Test public void with_header() throws IOException {
        Iterable<?> data = asList(
                asList("Header 1", "Header 2", "Header 3"),
                asList("one", "two", "three"),
                asList("four", "five", "siiiiiiix"));
        approver.assertApproved(data, TableFormatter.withHeader());
    }
}
