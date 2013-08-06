package org.rococoa.okeydoke.examples;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.rococoa.okeydoke.formatters.TableFormatter;
import org.rococoa.okeydoke.internal.MappedIterable;
import org.rococoa.okeydoke.internal.Mapper;
import org.rococoa.okeydoke.pickle.Feature;
import org.rococoa.okeydoke.pickle.FeatureRule;
import org.rococoa.okeydoke.pickle.Scenario;
import org.rococoa.okeydoke.pickle.ScenarioRule;

import java.util.List;

import static java.util.Arrays.asList;
import static org.rococoa.okeydoke.junit.ApprovalsRule.fileSystemRule;

@Feature(
        value = "Addition",
        inOrder = "to avoid silly mistakes",
        as = "a math idiot",
        iWant = "to be told the sum of lots of numbers")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PickleTablesTest {

    @ClassRule public static final FeatureRule feature = new FeatureRule(fileSystemRule("src/test/java"));
    @Rule public final ScenarioRule scenario = feature.scenarioRule();

    private Calculator calculator;

    @Before
    public void setUp() {
        scenario.given("I have a calculator");
        calculator = new Calculator();
    }

    @Scenario("Lots of numbers")
    @Test public void _1_table_formatting() {
        scenario.when("I add numbers");
        List<Object[]> table = asList(
                additionAsArray(42, 99),
                additionAsArray(42, -99),
                additionAsArray(-42, -99)
        );
        scenario.then("the result should be\n", TableFormatter.withHeader("Op1", "Op2", "sum"), table);
    }

    @Scenario("Lots of numbers with mapping")
    @Test public void _2_table_formatting_mapped() {
        scenario.when("I add numbers");

        List<Addition> table = asList(
                additionAsObject(42, 99),
                additionAsObject(42, -99),
                additionAsObject(-42, -99)
        );
        Iterable<Object[]> mappedTable = MappedIterable.map(table, new Mapper<Addition, Object[]>() {
            public Object[] map(Addition next) {
                return new Object[] {next.i1, next.i2, next.display};
            }
        });
        scenario.then("the result should be\n", TableFormatter.withHeader("Op1", "Op2", "sum"), mappedTable);
    }

    private Object[] additionAsArray(int i1, int i2) {
        Addition addition = additionAsObject(i1, i2);
        return new Object[] {addition.i1, addition.i2, addition.display};
    }

    private Addition additionAsObject(int i1, int i2) {
        Calculator calculator = new Calculator();
        calculator.enter(i1);
        calculator.enter(i2);
        calculator.add();
        return new Addition(i1, i2, calculator.display());
    }

    private class Addition {
        final int i1;
        final int i2;
        final String display;

        public Addition(int i1, int i2, String display) {
            this.i1 = i1;
            this.i2 = i2;
            this.display = display;
        }
    }
}
