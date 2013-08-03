package org.rococoa.okeydoke.examples;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.pickle.AFeature;
import org.rococoa.okeydoke.pickle.Scenario;

@AFeature(
        description = "Behaviour Driven Development",
        inOrder = "to make my life simpler",
        asA = "programmer",
        iWant = "okeydoke to manage scenarios for me")
public class PickleFeatureTest {

    @ClassRule public static final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");
    @Rule public final ScenarioRule scenarioRule = new ScenarioRule(approver);

    @Test public void add_two_numbers() {
        Scenario scenario = scenarioRule.scenario();
        scenario.given("I have a calculator");
        PickleTest.Calculator calculator = new PickleTest.Calculator();

        int i1 = 42;
        int i2 = 99;

        scenario.given("I have entered", i1, "into the calculator");
        calculator.enter(i1);

        scenario.and("I have entered", i2, "into the calculator");
        calculator.enter(i2);

        scenario.when("I press add");
        calculator.add();

        scenario.then("the result should be", calculator.display());
    }

    @Test public void add_a_positive_to_a_negative_number() {
        Scenario scenario = scenarioRule.scenario();
        scenario.given("I have a calculator");
        PickleTest.Calculator calculator = new PickleTest.Calculator();

        int i1 = 42;
        int i2 = -99;

        scenario.given("I have entered", i1, "into the calculator");
        calculator.enter(i1);

        scenario.and("I have entered", i2, "into the calculator");
        calculator.enter(i2);

        scenario.when("I press add");
        calculator.add();

        scenario.then("the result should be", calculator.display());
    }

    public static class ScenarioRule extends TestWatcher {
        private final ApprovalsRule approver;
        private Description description;

        public ScenarioRule(ApprovalsRule approver) {
            this.approver = approver;
        }

        @Override
        protected void starting(Description description) {
            this.description = description;
        }

        public Scenario scenario() {
            approver.transcript().endl();
            return new Scenario(approver.transcript(), 4);
        }
    }
}
