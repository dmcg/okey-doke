package org.rococoa.okeydoke.bdd;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;

public class BDDTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test public void scenario() throws IOException {
        Pickle pickle = new Pickle(approver.transcript());
        Scenario scenario = pickle.scenario("Add two numbers");
        scenario.given("I have a calculator");
        scenario.given("I have entered", 42, "into the calculator");
        scenario.and("I have entered", 99, "into the calculator");
        scenario.when("I press add");
        scenario.then("the result should be", add(42, 99));
    }

    private int add(int i, int i1) {
        return i + i1;
    }

}
