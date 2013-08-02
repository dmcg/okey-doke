package org.rococoa.okeydoke.junit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

import static org.rococoa.okeydoke.junit.TheoryApprovalsRule.fileSystemRule;

@RunWith(Theories.class)
public class TheoryApprovalsRuleTest {

    @ClassRule public static final TheoryApprovalsRule theoryRule = fileSystemRule("src/test/java");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @DataPoints public static final String[] FRUITS = { "apple", "banana", "cucumber" };

    @Theory
    public void string_length(String s) {
        approver.lockDownResult(s.length(), s);
    }

    @Theory
    public void legacyMethod_output(String s) {
        approver.lockDownResult(legacyMethod(s, s.length()), s, s.length());
    }

    @Theory
    public void legacyMethod_output_reflectively(String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        approver.lockDownReflectively(this, "legacyMethod", s, s.length());
    }

    @Theory
    public void legacyMethod_output_reflectively_overloaded(String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        approver.lockDownReflectively(this, "legacyMethod", s);
    }

    @Theory
    public void legacyMethod_output_reflectively_overloaded2(String s) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        approver.lockDownReflectively(this, "legacyMethod", s, s);
    }

    @Theory
    public void can_pass_class_for_static_methods(String s) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
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
