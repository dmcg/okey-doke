package org.hamcrest.approvals;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TheoryApprovalsRuleTest {

    // Note that this test is very simple because we can approve the output, and any tweaks we make to it

    @ClassRule public static final TheoryApprovalsRule theoryRule = new TheoryApprovalsRule("src/test/java");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @DataPoints
    public static String[] data() {
        return new String[] {
                "apple", "banana", "cucumber" };
    }

    @Theory
    public void legacyMethod_output(String s) {
        approver.lockDown(legacyMethod(s, s.length()), s, s.length());
    }

    @Theory
    public void string_length(String s) {
        approver.lockDown(s.length(), s);
    }


    public String legacyMethod(String s, int i) {
        return s + i;
    }

}
