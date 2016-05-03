package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.formatters.TableFormatter;
import com.oneeyedmen.okeydoke.internal.Mapper;
import com.oneeyedmen.okeydoke.pickle.FeatureRule;
import com.oneeyedmen.okeydoke.pickle.Scenario;
import com.oneeyedmen.okeydoke.pickle.ScenarioRule;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;

import static com.oneeyedmen.okeydoke.junit.ApprovalsRule.usualRule;
import static com.oneeyedmen.okeydoke.pickle.FeatureInfo.featureNamed;
import static java.util.Arrays.asList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PickleTablesTest {

    @ClassRule public static final FeatureRule feature = new FeatureRule(
            featureNamed("Addition").asA("a math idiot").inOrder("to avoid silly mistakes").iWant("to be told the sum of lots of numbers"),
            usualRule());
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
        scenario.then("the result should be\n", new TableFormatter().withHeaders("Op1", "Op2", "sum"), table);
    }

    @Scenario("Lots of numbers with mapping")
    @Test public void _2_table_formatting_mapped() {
        scenario.when("I add numbers");

        List<Addition> table = asList(
                additionAsObject(42, 99),
                additionAsObject(42, -99),
                additionAsObject(-42, -99)
        );
        Mapper<Addition, Object[]> mapper = new Mapper<Addition, Object[]>() {
            public Object[] map(Addition next) {
                return new Object[]{next.i1, next.i2, next.display};
            }
        };
        scenario.then("the result should be\n", new TableFormatter().withHeaders("Op1", "Op2", "sum").withMapper(mapper), table);
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
