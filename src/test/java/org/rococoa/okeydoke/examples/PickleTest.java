package org.rococoa.okeydoke.examples;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.rococoa.okeydoke.formatters.TableFormatter;
import org.rococoa.okeydoke.internal.MappingIterable;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.pickle.Description;
import org.rococoa.okeydoke.pickle.FeatureRule;
import org.rococoa.okeydoke.pickle.Scenario;
import org.rococoa.okeydoke.pickle.ScenarioRule;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.rococoa.okeydoke.junit.ApprovalsRule.fileSystemRule;

@Description(
        value = "Addition",
        inOrder = "to avoid silly mistakes",
        as = "a math idiot",
        iWant = "to be told the sum of two numbers")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PickleTest {

    public static final ApprovalsRule approvalsRule = fileSystemRule("src/test/java");
    @ClassRule public static final FeatureRule feature = new FeatureRule(approvalsRule);
    @ClassRule public static final ApprovalsRule alias = approvalsRule;

    @Rule public final ScenarioRule scenarioRule = feature.scenarioRule();

    private Scenario scenario;
    private PickleTest.Calculator calculator;

    @Before
    public void setUp() {
        scenario = scenarioRule.scenario();
        scenario.given("I have a calculator");
        calculator = new PickleTest.Calculator();
    }

    @Description("Add two numbers")
    @Test public void _1_add_two_numbers() {
        add(42, 99);
    }

    @Description("Add a positive to a negative number")
    @Test public void _2_add_a_positive_to_a_negative_number() {
        add(42, -99);
    }

    @Description("Lots of numbers")
    @Test public void _3_table_formatting() {
        scenario.when("I add numbers");
        scenario.then("the result should be");

        List<Object[]> table = asList(
                additionAsArray(42, 99),
                additionAsArray(42, -99),
                additionAsArray(-42, -99)
        );
        scenario.appendFormatted(table, TableFormatter.withHeader("Op1", "Op2", "sum"));
    }

    @Description("Lots of numbers with mapping")
    @Test public void _4_table_formatting_mapped() {
        scenario.when("I add numbers");
        scenario.then("the result should be");

        List<Addition> table = asList(
                additionAsObject(42, 99),
                additionAsObject(42, -99),
                additionAsObject(-42, -99)
        );
        Iterable<?> mappedTable = new MappingIterable<Object, Addition>(table) {
            @Override protected Object map(Addition next) {
                return new Object[] {next.i1, next.i2, next.display};
            }
        };

        scenario.appendFormatted(mappedTable, TableFormatter.withHeader("Op1", "Op2", "sum"));
    }

    private void add(int i1, int i2) {
        scenario.given("I have entered", i1, "into the calculator");
        calculator.enter(i1);

        scenario.and("I have entered", i2, "into the calculator");
        calculator.enter(i2);

        scenario.when("I press add");
        calculator.add();

        scenario.then("the result should be", calculator.display());
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

    private static class Calculator {
        private final LinkedList<Integer> stack = new LinkedList<Integer>();
        private int display;

        public void enter(int n) {
            stack.push(n);
        }

        public void add() {
            display = stack.pop() + stack.pop();
        }

        public String display() {
            return String.valueOf(display);
        }
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
