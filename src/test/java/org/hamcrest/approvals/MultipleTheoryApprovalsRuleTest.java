package org.hamcrest.approvals;

import org.junit.ClassRule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class MultipleTheoryApprovalsRuleTest {

    /*
        NB IntelliJ seems to see this as a failure if the .approved file isn't there, but looses the output that tells you
     */

    @ClassRule public static final TheoryApprovalsRule approver = new TheoryApprovalsRule("src/test/java", MultipleTheoryApprovalsRuleTest.class);


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
        approver.lockDown(s.length(), s.length());
    }


    public String legacyMethod(String s, int i) {
        return s + i;
    }

}
