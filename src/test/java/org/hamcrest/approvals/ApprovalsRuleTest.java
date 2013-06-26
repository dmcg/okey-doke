package org.hamcrest.approvals;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.approvals.CleanDirectoryRule.dirForPackage;
import static org.junit.Assert.*;

public class ApprovalsRuleTest {

    @Rule public final CleanDirectoryRule clean = new CleanDirectoryRule(dirForPackage("src/test/java", this), CleanDirectoryRule.AFTER_TOO);
    @Rule public final ApprovalsRule approver = new ApprovalsRule("src/test/java", this);

    @Test public void doesnt_match_where_no_approved_result() {
        assertThat("banana", not(approver.isAsApproved()));
    }

    @Test public void matches_when_approved_result_matches() throws IOException {
        approver.approve("banana");
        assertThat("banana", approver.isAsApproved());
    }

    @Test public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        approver.approve("banana");
        assertThat("kumquat", not(approver.isAsApproved()));
    }

    @Test public void look_at_the_nice_messages_from_hamcrest() throws IOException {
        try {
            Assert.assertThat("Deliberate failure - Jackdaws adore my big sphincter of quartz", approver.isAsApproved());
            fail("Unexpected non-assertion");
        } catch (AssertionError expected) {
            System.out.println(expected);
        }

        approver.approve("Deliberate failure - Jackdaws love my big sphinx of quartz");
        try {
            Assert.assertThat("Deliberate failure - Jackdaws adore my big sphincter of quartz", approver.isAsApproved());
            fail("Unexpected non-assertion");
        } catch (AssertionError expected) {
            System.out.println(expected);
        }
    }

    @Test public void can_force_approval_by_editing_the_matcher() throws IOException {
        Assert.assertThat("banana", approver.FORCE_APPROVAL());
        Assert.assertThat("banana", approver.isAsApproved());
    }

    @Test public void writes_approved_file_in_package() throws IOException {
        assertEquals(
                new File(dirForPackage("src/test/java", this), "writes_approved_file_in_package.approved"),
                approver.approvedFile());

        assertFalse(approver.approvedFile().exists());

        approver.approve(this);
        assertTrue(approver.approvedFile().exists());
    }

}
