package org.hamcrest.approvals;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.InRange;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;

@Ignore("WIP")
@RunWith(org.junit.contrib.theories.Theories.class)
public class QuickCheckTest {

    /*
      This seems like a good idea, but junit-quickcheck generators aren't stable
     */

    @ClassRule public static final TheoryApprovalsRule theoryRule = new TheoryApprovalsRule("src/test/java");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @org.junit.contrib.theories.Theory
    public void legacyMethod_output(@ForAll(sampleSize = 3) Strings s, @ForAll(sampleSize = 3) @InRange(minInt = 0, maxInt = 2) int i, @ForAll(sampleSize = 2) boolean b) {
        approver.lockDown(legacyMethod(s.name(), i, b), s.name(), i, b);
    }


    public String legacyMethod(String s, int i, boolean b) {
        return s + i + b;
    }

    public static enum Strings {
        apple, banana, kumquat
    }
}
