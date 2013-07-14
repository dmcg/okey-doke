package org.rococoa.okeydoke.junit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.rococoa.okeydoke.junit.TheoryApprovalsRule.fileSystemRule;

@RunWith(Theories.class)
public class TheoryApprovalsRuleTest {

    // Note that this test is very simple because we can approve the output, and any tweaks we make to it

    @ClassRule public static final TheoryApprovalsRule theoryRule = fileSystemRule("src/test/java");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @DataPoints public static final String[] FRUITS = { "apple", "banana", "cucumber" };

    @Theory
    public void string_length(String s) throws IOException {
        approver.lockDown(s.length(), s);
    }

    @Theory
    public void legacyMethod_output(String s) throws IOException {
        approver.lockDown(legacyMethod(s, s.length()), s, s.length());
    }

    @Theory
    public void legacyMethod_output_reflectively(String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        approver.lockDownReflectively(this, "legacyMethod", s, s.length());
    }

    @Theory
    public void legacyMethod_output_reflectively_overloaded(String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        approver.lockDownReflectively(this, "legacyMethod", s);
    }

    @Theory
    public void legacyMethod_output_reflectively_overloaded2(String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        approver.lockDownReflectively(this, "legacyMethod", s, s);
    }

    @Theory
    public void can_pass_class_for_static_methods(String s) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        approver.lockDownReflectively(this.getClass(), "legacyMethod", s, s.length());
    }

    public static String legacyMethod(String s, int i) {
        return s + i;
    }

    public static String legacyMethod(String s1, Object s2) {
        return s1 + s2;
    }

    public static String legacyMethod(String s) {
        return s;
    }

}
