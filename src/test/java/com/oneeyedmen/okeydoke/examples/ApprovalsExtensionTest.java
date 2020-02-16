package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.Approver;
import com.oneeyedmen.okeydoke.junit5.ApprovalsExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//README_TEXT
@ExtendWith(ApprovalsExtension.class)
public class ApprovalsExtensionTest {


    @Test
    public void doesnt_match_where_no_approved_result(Approver approver) throws IOException {
        whenApprovedIs(null, approver);
        try {
            approver.assertApproved("banana");
            fail("should have thrown");
        } catch (AssertionError expected) {
        }
    }

    @Test
    public void matches_when_approved_result_matches(Approver approver) throws IOException {
        whenApprovedIs("banana", approver);
        approver.assertApproved("banana");
    }

    @Test
    public void doesnt_match_when_approved_result_doesnt_match(Approver approver) throws IOException {
        whenApprovedIs("banana", approver);
        try {
            approver.assertApproved("kumquat");
            fail("should have thrown");
        } catch (AssertionFailedError expected) {
            assertEquals("kumquat", expected.getActual().getValue());
            assertEquals("banana", expected.getExpected().getValue());
        }
    }

    //README_TEXT

    @Disabled("Unignore to see no approval in IDE")
    @Test public void see_how_my_IDE_reports_no_approval(Approver approver) throws IOException {
        whenApprovedIs(null, approver);
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    @Disabled("Unignore to see failure report in IDE")
    @Test public void see_how_my_IDE_reports_diffs(Approver approver) throws IOException {
        whenApprovedIs("Deliberate failure - Jackdaws love my big sphinx of quartz", approver);
        approver.assertApproved("Deliberate failure - Jackdaws peck my big sphincter of quartz");
    }

    private void whenApprovedIs(String valueOrNull, Approver approver) throws IOException {
        if (valueOrNull == null)
            approver.removeApproved();
        else
            approver.makeApproved(valueOrNull);
    }
}
