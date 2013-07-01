package org.hamcrest.approvals;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.ValuesOf;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(org.junit.contrib.theories.Theories.class)
public class QuickCheckTest {

    @ClassRule public static final TheoryApprovalsRule theoryRule = new TheoryApprovalsRule("src/test/java");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @org.junit.contrib.theories.Theory
    public void legacyMethod_output(@ForAll @ValuesOf Strings s, @ForAll @ValuesOf Ints  i, @ForAll @ValuesOf boolean b) {
        approver.lockDown(legacyMethod(s.name(), i.value, b), s.name(), i.value, b);
    }


    public String legacyMethod(String s, int i, boolean b) {
        return s + i + b;
    }

    public static enum Strings {
        apple, banana, kumquat;
    }

    public static enum Ints {
        minus_one(-1), zero(0), one(1), two(2), forty_two(42);

        public  final int value;

        Ints(int value) {
            this.value = value;
        }
    }
}
