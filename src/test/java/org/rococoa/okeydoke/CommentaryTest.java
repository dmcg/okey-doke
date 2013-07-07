package org.rococoa.okeydoke;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;

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
        approver.assertApproved(new Song(fruit, count));
    }

    private class Song {
        private final String fruit;
        private final int count;

        public Song(String fruit, int count) {
            this.fruit = fruit;
            this.count = count;
        }

        @Override
        public String toString() {
            return "yes we have " + count + " " + fruit + "(s)";
        }
    }
}