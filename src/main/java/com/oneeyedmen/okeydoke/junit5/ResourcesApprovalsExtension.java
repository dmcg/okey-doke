package com.oneeyedmen.okeydoke.junit5;


/**
 * A JUnit 5 Extension to provide an Approver as a parameter to test functions.
 *
 * Stores approved files in src/test/resources
 */
public class ResourcesApprovalsExtension extends ApprovalsExtension {
    public ResourcesApprovalsExtension() {
        super("src/test/resources");
    }
}
