package org.rococoa.okeydoke.examples;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.formatters.TableFormatter;
import org.rococoa.okeydoke.internal.MappingIterable;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.pickle.Feature;
import org.rococoa.okeydoke.pickle.Pickle;
import org.rococoa.okeydoke.pickle.Scenario;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class PickleTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void scenarios_from_feature() {
        Pickle pickle = new Pickle(approver.transcript());
        Feature feature = pickle.feature("Addition").inOrder("to avoid silly mistakes").
                asA("math idiot").
                iWant("to be told the sum of two numbers");

        addition(feature.scenario("Add two numbers"), 42, 99);
        addition(feature.scenario("Add a positive to a negative number"), 42, -99);
    }

    @Test public void direct_to_scenario() {
        Pickle pickle = new Pickle(approver.transcript());
        addition(pickle.scenario("Add two numbers"), 42, 99);
    }

    @Test public void table_formatting() {
        Pickle pickle = new Pickle(approver.transcript());
        Scenario addition = pickle.scenario("Add two numbers");
        addition.given("I have a calculator");
        addition.when("I add numbers");
        addition.then("the result should be");

        List<Object[]> table = asList(
                additionAsArray(42, 99),
                additionAsArray(42, -99),
                additionAsArray(-42, -99)
        );
        addition.appendFormatted(table, TableFormatter.withHeader("Op1", "Op2", "sum"));
    }

    @Test public void table_formatting_mapped() {
        Pickle pickle = new Pickle(approver.transcript());
        Scenario addition = pickle.scenario("Add two numbers");
        addition.given("I have a calculator");
        addition.when("I add numbers");
        addition.then("the result should be");

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

        addition.appendFormatted(mappedTable, TableFormatter.withHeader("Op1", "Op2", "sum"));
    }

    private void addition(Scenario scenario, int i1, int i2) {
        scenario.given("I have a calculator");
        Calculator calculator = new Calculator();

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

    public static class Calculator {
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

    class Addition {
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
