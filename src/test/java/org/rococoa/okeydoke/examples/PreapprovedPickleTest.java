package org.rococoa.okeydoke.examples;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.rococoa.okeydoke.pickle.Feature;
import org.rococoa.okeydoke.pickle.FeatureRule;
import org.rococoa.okeydoke.pickle.Scenario;
import org.rococoa.okeydoke.pickle.ScenarioRule;

import static org.hamcrest.Matchers.equalTo;
import static org.rococoa.okeydoke.junit.ApprovalsRule.fileSystemRule;

@Feature(
        value = "Addition",
        inOrder = "to avoid silly mistakes",
        as = "a math idiot",
        iWant = "to be told the sum of two numbers")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PreapprovedPickleTest {

    @ClassRule public static final FeatureRule feature = new FeatureRule(fileSystemRule("src/test/java"));
    @Rule public final ScenarioRule scenario = feature.scenarioRule();

    private Calculator calculator;

    @Before
    public void setUp() {
        scenario.given("I have a calculator");
        calculator = new Calculator();
    }

    @Scenario("Add two numbers successfully")
    @Test public void _1_add_two_numbers() {
        add(42, 99, "141");
    }

    @Test(expected = AssertionError.class)
    public void _2_add_two_numbers_unsuccessfully() {
        add(42, 99, "142");
    }

    private void add(int i1, int i2, String expected) {
        scenario.given("I have entered", i1, "into the calculator");
        calculator.enter(i1);

        scenario.and("I have entered", i2, "into the calculator");
        calculator.enter(i2);

        scenario.when("I press add");
        calculator.add();

        scenario.thenAssertThat("the result should be", calculator.display(), equalTo(expected));
    }
}
