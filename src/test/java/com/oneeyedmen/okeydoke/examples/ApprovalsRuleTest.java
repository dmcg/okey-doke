package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.junit4.ApprovalsRule;
import org.junit.Rule;
import org.junit.Test;

import static com.oneeyedmen.okeydoke.examples.Support.doSomeCalculation;

public class ApprovalsRuleTest {

    //README_TEXT
    @Rule public final ApprovalsRule approver = ApprovalsRule.usualRule();

    @Test
    public void something_that_we_want_to_be_the_same_next_time(
    ) {
        Object result = doSomeCalculation(42, "banana");
        approver.assertApproved(result); // check that the result is as approved
    }
    //README_TEXT
}
