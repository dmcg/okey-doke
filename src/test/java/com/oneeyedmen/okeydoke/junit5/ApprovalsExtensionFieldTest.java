package com.oneeyedmen.okeydoke.junit5;

import com.oneeyedmen.okeydoke.Approver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opentest4j.AssertionFailedError;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApprovalsExtensionFieldTest {

    @RegisterExtension
    ApprovalsExtension approvals = new ApprovalsExtension();

    private static File dir = new File("src/test/java/com/oneeyedmen/okeydoke/junit5/");

    @AfterEach
    public void cleanupFiles() {
        new File(dir, "ApprovalsExtensionFieldTest.shouldFailInvalidOutput.actual").delete();
        new File(dir, "ApprovalsExtensionFieldTest.shouldRetainTheActualOutputOnFailure.actual").delete();
    }

    @Test
    public void shouldPassValidOutput(Approver approver) {
        approver.assertApproved("valid output");
    }

    @Test
    public void shouldFailInvalidOutput(Approver approver) {
        assertThrows(AssertionFailedError.class, () -> approver.assertApproved("invalid output"));
    }

    @Test
    public void shouldRetainTheActualOutputOnFailure(Approver approver) {
        try {
            approver.assertApproved("invalid output");
        } catch (Throwable ignored) {
        }

        assertTrue(new File(dir, "ApprovalsExtensionFieldTest.shouldRetainTheActualOutputOnFailure.actual").exists());
    }
}
