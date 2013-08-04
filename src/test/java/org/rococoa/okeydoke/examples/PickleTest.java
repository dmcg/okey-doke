package org.rococoa.okeydoke.examples;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.rococoa.okeydoke.pickle.Feature;
import org.rococoa.okeydoke.pickle.FeatureRule;
import org.rococoa.okeydoke.pickle.Scenario;
import org.rococoa.okeydoke.pickle.ScenarioRule;

import static org.rococoa.okeydoke.junit.ApprovalsRule.fileSystemRule;

@Feature(
        value = "Addition",
        inOrder = "to avoid silly mistakes",
        as = "a math idiot",
        iWant = "to be told the sum of two numbers")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PickleTest {

    @ClassRule public static final FeatureRule feature = new FeatureRule(fileSystemRule("src/test/java"));
    @Rule public final ScenarioRule scenario = feature.scenarioRule();

    private Calculator calculator;

    @Before
    public void setUp() {
        scenario.given("I have a calculator");
        calculator = new Calculator();
    }

    @Scenario("Add two numbers")
    @Test public void _1_add_two_numbers() {
        add(42, 99);
    }

    @Scenario("Add a positive to a negative number")
    @Test public void _2_add_a_positive_to_a_negative_number() {
        add(42, -99);
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
