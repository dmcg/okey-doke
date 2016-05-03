package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.Formatters;
import com.oneeyedmen.okeydoke.Invocation;
import com.oneeyedmen.okeydoke.internal.Fred;
import com.oneeyedmen.okeydoke.pickle.FeatureRule;
import com.oneeyedmen.okeydoke.pickle.Scenario;
import com.oneeyedmen.okeydoke.pickle.ScenarioRule;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.oneeyedmen.okeydoke.junit.ApprovalsRule.usualRule;
import static com.oneeyedmen.okeydoke.pickle.FeatureInfo.featureNamed;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExperimentalPickleTest {

    @ClassRule public static final FeatureRule feature = new FeatureRule(
            featureNamed("Addition").asA("a math idiot").inOrder("to avoid silly mistakes").iWant("to be told the sum of two numbers"),
            usualRule());
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
        thenWith(scenario, this, "the result is ").add(42, 42);
        thenWith(scenario, this, "the result is ").add(42, -99);
    }

    private static  <T> T thenWith(final ScenarioRule scenario, final T object, final String s) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = method.invoke(object, args);
                scenario.then(s, Formatters.invocationFormatter(), new Invocation(args, result));
                return result;
            }
        };
        return (T) Fred.newProxyInstance(object.getClass(), handler);
    }

    // must be protected to allow overriding in proxy
    protected String add(int i1, int i2) {
        calculator.enter(i1);
        calculator.enter(i2);
        calculator.add();
        return calculator.display();
    }
}
