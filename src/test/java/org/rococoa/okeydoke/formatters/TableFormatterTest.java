package org.rococoa.okeydoke.formatters;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.Formatters;
import org.rococoa.okeydoke.internal.Mapper;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import static java.util.Arrays.asList;

public class TableFormatterTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void array_is_listed_by_row() {
        approver.assertApproved(new String[] {"one", "two", "three"}, Formatters.instance());
    }

    @Test public void array_table_is_laid_out() {
        String [][] data = {
                {"one", "two", "three"},
                {"four", "five", "siiiiiiix"}};
        approver.assertApproved(data, Formatters.instance());
    }

    @Test public void iterable_is_listed_by_row() {
        approver.assertApproved(asList("one", "two", "three"), Formatters.instance());
    }

    @Test public void iterable_table_is_laid_out() {
        Iterable<?> data = asList(
                asList("one", "two", "three"),
                asList("four", "five", "siiiiiiix"));
        approver.assertApproved(data, Formatters.instance());
    }

    @Test public void null_is_printed() {
        approver.assertApproved(null);
    }

    @Test public void with_header() {
        Iterable<?> data = asList(
                asList("one", "two", "three"),
                asList("four", "five", "siiiiiiix"));
        approver.assertApproved(data, new TableFormatter().withHeaders("Header 1", "Header 2", "Header 3"));
    }

    @Test public void with_mapper() {
        Iterable<?> data = asList("one", "two", "three");
        Mapper<String, Object[]> mapper = new Mapper<String, Object[]>() {
            @Override public Object[] map(String next) {
                return new Object[] {next, next.length()};
            }
        };
        approver.assertApproved(data, new TableFormatter().withHeaders("String", "Length").withMapper(mapper));
    }


}
