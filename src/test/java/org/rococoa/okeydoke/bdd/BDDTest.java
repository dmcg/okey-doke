package org.rococoa.okeydoke.bdd;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;

public class BDDTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void scenario() throws IOException {
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

    private int add(int i, int i1) {
        return i + i1;
    }

}
