package org.hamcrest.approvals;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.fail;

@RunWith(Theories.class)
public class TheoryApprovalsRuleReflectionTest {

    // Here we show what happens when reflection goes bad

    private final TheoryApprovalsRule.TheoryApprover approver = TheoryApprovalsRule.fileSystemRule("src/test/java").approver();

    @Test
    public void legacyMethod_output_reflectively_no_such_method_name() throws InvocationTargetException, IllegalAccessException {
        try {
            approver.lockDownReflectively(TheoryApprovalsRuleTest.class, "noSuchMethod", "banana", 42);
            fail("expected exception");
        } catch (NoSuchMethodException expected) {
        }
    }

    @Test
    public void legacyMethod_output_reflectively_no_method_with_parameter_count() throws InvocationTargetException, IllegalAccessException {
        try {
            approver.lockDownReflectively(TheoryApprovalsRuleTest.class, "legacyMethod", "one", "two", "three");
            fail("expected exception");
        } catch (NoSuchMethodException expected) {
        }
    }

    @Test
    public void legacyMethod_output_reflectively_wrong_parameter_types() throws InvocationTargetException, IllegalAccessException {
        try {
            approver.lockDownReflectively(TheoryApprovalsRuleTest.class, "legacyMethod", 42, "banana");
            fail("expected exception");
        } catch (NoSuchMethodException expected) {
        }
    }

}
