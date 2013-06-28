package org.hamcrest.approvals;

import org.junit.ClassRule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class SingleTheoryApprovalsRuleTest {

    @ClassRule public static final TheoryApprovalsRule approver = new TheoryApprovalsRule("src/test/java");


    @DataPoints
    public static String[] data() {
        return new String[] {
                "apple", "banana", "cucumber" };
    }

    @Theory
    public void legacyMethod_repeats_input(String s) {
        approver.lockDown(legacyMethod(s, s.length()), s, s.length());
    }

    public String legacyMethod(String s, int i) {
        return s + i;
    }

}
