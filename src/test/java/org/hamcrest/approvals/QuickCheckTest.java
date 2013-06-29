package org.hamcrest.approvals;

import com.pholser.junit.quickcheck.ForAll;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;

@Ignore("WIP")
@RunWith(org.junit.contrib.theories.Theories.class)
public class QuickCheckTest {

    @ClassRule public static final TheoryApprovalsRule theoryRule = new TheoryApprovalsRule("src/test/java");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @org.junit.contrib.theories.Theory
    public void legacyMethod_output(@ForAll String s, @ForAll int i) {
        approver.lockDown(legacyMethod(s, i), s, i);
    }


    public String legacyMethod(String s, int i) {
        return s + i;
    }

}
