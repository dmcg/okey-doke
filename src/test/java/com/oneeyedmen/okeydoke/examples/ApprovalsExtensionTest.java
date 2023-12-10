package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.Approver;
import com.oneeyedmen.okeydoke.junit5.ApprovalsExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;

import static com.oneeyedmen.okeydoke.examples.Support.doSomeCalculation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//README_TEXT
public class ApprovalsExtensionTest {

    // Initialise okey-doke.
    @RegisterExtension ApprovalsExtension approvals = new ApprovalsExtension();
        // See other constructors to change where the files are stored,
        // or change the extension

    @Test
    public void something_that_we_want_to_be_the_same_next_time(
            Approver approver // approver will be injected
    ) {
        Object result = doSomeCalculation(42, "banana");
        approver.assertApproved(result); // check that the result is as approved
    }
}
