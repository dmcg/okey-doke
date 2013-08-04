package org.rococoa.okeydoke.examples;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.rococoa.okeydoke.Transcript;
import org.rococoa.okeydoke.junit.ApprovalsRule;
import org.rococoa.okeydoke.pickle.Description;
import org.rococoa.okeydoke.pickle.Scenario;

import static org.rococoa.okeydoke.junit.ApprovalsRule.fileSystemRule;

@Description(
        value = "Addition",
        inOrder = "to avoid silly mistakes",
        as = "a math idiot",
        iWant = "to be told the sum of two numbers")
public class PickleFeatureTest {

    public static final ApprovalsRule approvalsRule = fileSystemRule("src/test/java");
    @ClassRule public static final FeatureRule feature = new FeatureRule(approvalsRule);
    @ClassRule public static final ApprovalsRule alias = approvalsRule;

    @Rule public final ScenarioRule scenarioRule = feature.scenarioRule();

    private Scenario scenario;
    private PickleTest.Calculator calculator;

    @Before public void setUp() {
        scenario = scenarioRule.scenario();
        scenario.given("I have a calculator");
        calculator = new PickleTest.Calculator();
    }

    @Description("Add two numbers")
    @Test public void add_two_numbers() {
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

    public static class FeatureRule extends TestWatcher {
        private final ApprovalsRule approvalsRule;

        public FeatureRule(ApprovalsRule approvalsRule) {
            this.approvalsRule = approvalsRule;
        }

        public ScenarioRule scenarioRule() {
            return new ScenarioRule(approvalsRule.transcript());
        }

        @Override
        protected void starting(org.junit.runner.Description description) {
            Description featureAnnotation = description.getTestClass().getAnnotation(Description.class);
            writeFeature(approvalsRule.transcript(), featureAnnotation);
        }

        private void writeFeature(Transcript transcript, Description description) {
            transcript.append("Feature: ").appendLine(description.value());
            transcript.space(4).append("In Order ").appendLine(description.inOrder());
            transcript.space(4).append("As ").appendLine(description.as());
            transcript.space(4).append("I want ").appendLine(description.iWant());
        }
    }

    public static class ScenarioRule extends TestWatcher {
        private final Transcript transcript;

        public ScenarioRule(Transcript transcript) {
            this.transcript = transcript;
        }

        @Override
        protected void starting(org.junit.runner.Description description) {
            transcript.space(4).endl();
            transcript.space(4).append("Scenario: ").appendLine(scenarioNameFor(description));
        }

        private String scenarioNameFor(org.junit.runner.Description description) {
            Description annotation = description.getAnnotation(Description.class);
            return annotation == null ? description.getMethodName() : annotation.value();
        }

        public Scenario scenario() {
            return new Scenario(transcript, 4);
        }
    }
}
