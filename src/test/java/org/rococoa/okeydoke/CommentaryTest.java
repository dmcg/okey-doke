package org.rococoa.okeydoke;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class CommentaryTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java", "target/approvals");

    @Test
    public void can_describe_what_we_are_doing_before_assertion() throws IOException {
        Transcript transcript = approver.transcript();
        transcript.appendLine("As a greengrocer").
            appendLine("I want to sing");
        check(transcript, "banana", 0);
        // the rule will check the actual output against approved
    }

    private void check(Transcript transcript, String fruit, int count) throws IOException {
        transcript.appendLine("Given " + fruit + " count " + count).
            append("I sing ").appendFormatted(new Song(fruit, count));
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