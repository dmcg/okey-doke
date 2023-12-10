package com.oneeyedmen.okeydoke.junit5;


/**
 * A JUnit 5 Extension to provide an Approver as a parameter to test functions.
 *
 * Stores approved files in src/test/kotlin
 *
 * Not really needed anymore.
 */
public class KotlinApprovalsExtension extends ApprovalsExtension {
    public KotlinApprovalsExtension() {
        super("src/test/kotlin");
    }
}
