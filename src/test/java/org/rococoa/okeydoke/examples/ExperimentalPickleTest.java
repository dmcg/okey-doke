package org.rococoa.okeydoke.examples;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.rococoa.okeydoke.internal.Fred;
import org.rococoa.okeydoke.pickle.Feature;
import org.rococoa.okeydoke.pickle.FeatureRule;
import org.rococoa.okeydoke.pickle.Scenario;
import org.rococoa.okeydoke.pickle.ScenarioRule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static org.rococoa.okeydoke.junit.ApprovalsRule.fileSystemRule;

@Feature(
        value = "Addition",
        inOrder = "to avoid silly mistakes",
        as = "a math idiot",
        iWant = "to be told the sum of two numbers")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExperimentalPickleTest {

    @ClassRule public static final FeatureRule feature = new FeatureRule(fileSystemRule("src/test/java"));
    @Rule public final ScenarioRule scenario = feature.scenarioRule();

    private Calculator calculator;

    @Before
    public void setUp() {
        scenario.given("I have a calculator");
        calculator = new Calculator();
    }

    @Scenario("Adding numbers")
    @Test public void _1_called_thenWith() {
        scenario.when("I enter numbers");
        thenWith(scenario, this, "the result is").add(42, 42);
        thenWith(scenario, this, "the result is").add(42, -99);
    }

    private static  <T> T thenWith(final ScenarioRule scenario, final T object, final String s) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = method.invoke(object, args);
                scenario.then(s, args, result);
                return result;
            }
        };
        return (T) Fred.newProxyInstance(object.getClass(), handler);
    }

    protected String add(int i1, int i2) {
        calculator.enter(i1);
        calculator.enter(i2);
        calculator.add();
        return calculator.display();
    }
}
