package org.rococoa.okeydoke.examples;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.formatters.TableFormatter;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.pickle.Feature;
import org.rococoa.okeydoke.pickle.Pickle;
import org.rococoa.okeydoke.pickle.Scenario;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PickleTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void scenarios_from_feature() throws IOException {
        Pickle pickle = new Pickle(approver.transcript());
        Feature feature = pickle.feature("Addition").inOrder("to avoid silly mistakes").
                asA("math idiot").
                iWant("to be told the sum of two numbers");
        Scenario addition = feature.scenario("Add two numbers");
        addition.given("I have a calculator");
        addition.given("I have entered", 42, "into the calculator");
        addition.and("I have entered", 99, "into the calculator");
        addition.when("I press add");
        addition.then("the result should be", add(42, 99));

        Scenario negativeAddition = feature.scenario("Add a positive to a negative number");
        negativeAddition.given("I have a calculator");
        negativeAddition.given("I have entered", 42, "into the calculator");
        negativeAddition.and("I have entered", -99, "into the calculator");
        negativeAddition.when("I press add");
        negativeAddition.then("the result should be", add(42, -99));
    }

    @Test public void direct_to_scenario() throws IOException {
        Pickle pickle = new Pickle(approver.transcript());
        Scenario addition = pickle.scenario("Add two numbers");
        addition.given("I have a calculator");
        addition.given("I have entered", 42, "into the calculator");
        addition.and("I have entered", 99, "into the calculator");
        addition.when("I press add");
        addition.then("the result should be", add(42, 99));
    }

    @Test public void table_formatting() throws IOException {
        Pickle pickle = new Pickle(approver.transcript());
        Scenario addition = pickle.scenario("Add two numbers");
        addition.given("I have a calculator");
        addition.when("I add numbers");
        addition.then("the result should be");

        List<?> table = Arrays.asList(
                row(42, 99),
                row(42, -99),
                row(-42, -99)
        );

        addition.appendFormatted(table, TableFormatter.withHeader("Op1", "Op2", "sum"));
    }

    private Object row(int i1, int i2) {
        return new int[] {i1, i2, add(i1, i2)};
    }

    private int add(int i1, int i2) {
        return i1 + i2;
    }
}
