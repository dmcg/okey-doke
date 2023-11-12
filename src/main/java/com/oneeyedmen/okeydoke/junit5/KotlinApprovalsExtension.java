package com.oneeyedmen.okeydoke.junit5;


/**
 * A JUnit 5 Extension to provide an Approver as a parameter to test functions.
 *
 * Stores approved files in src/test/kotlin
 */
public class KotlinApprovalsExtension extends ApprovalsExtension {
    public KotlinApprovalsExtension() {
        super("src/test/kotlin");
    }
}
