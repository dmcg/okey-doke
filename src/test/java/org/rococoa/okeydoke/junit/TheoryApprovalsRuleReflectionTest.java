package org.rococoa.okeydoke.junit;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import static org.rococoa.okeydoke.junit.TheoryApprovalsRule.fileSystemRule;

@RunWith(Theories.class)
public class TheoryApprovalsRuleReflectionTest {

    // Here we show what happens when reflection goes bad

    private final TheoryApprovalsRule.TheoryApprover approver = fileSystemRule("src/test/java").approver();

    @Test(expected = NoSuchMethodException.class)
    public void legacyMethod_output_reflectively_no_such_method_name() throws Exception {
        approver.lockDownReflectively(TheoryApprovalsRuleTest.class, "noSuchMethod", "banana", 42);
    }

    @Test(expected = NoSuchMethodException.class)
    public void legacyMethod_output_reflectively_no_method_with_parameter_count() throws Exception {
        approver.lockDownReflectively(TheoryApprovalsRuleTest.class, "legacyMethod", "one", "two", "three");
    }

    @Test(expected = NoSuchMethodException.class)
    public void legacyMethod_output_reflectively_wrong_parameter_types() throws Exception {
        approver.lockDownReflectively(TheoryApprovalsRuleTest.class, "legacyMethod", 42, "banana");
    }

}
