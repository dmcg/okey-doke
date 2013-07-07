package org.rococoa.okeydoke;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.testutils.CleanDirectoryRule;

import java.io.IOException;
import java.io.PrintStream;

import static org.rococoa.okeydoke.testutils.CleanDirectoryRule.dirForPackage;

public class CommentaryTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java", "target/approvals");

    @Test
    public void can_describe_what_we_are_doing_before_assertion() throws IOException {
        PrintStream printStream = approver.printStream();
        printStream.println("As a greengrocer");
        printStream.println("I want to sing");
        check(printStream, "banana", 0);
    }

    private void check(PrintStream printStream, String fruit, int count) throws IOException {
        printStream.println("Given " + fruit + " count " + count);
        printStream.print("I sing ");
        approver.assertApproved(describe(fruit, count));
    }

    private static String describe(String fruit, int count) {
        return "yes we have " + count + " " + fruit + "(s)";
    }

}