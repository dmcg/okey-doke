package org.rococoa.okeydoke.examples;

import org.junit.Rule;
import org.junit.Test;
import org.rococoa.okeydoke.Transcript;
import org.rococoa.okeydoke.junit.ApprovalsRule;

import java.io.IOException;

public class TranscriptTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/java");

    @Test
    public void can_describe_what_we_are_doing_before_assertion() throws IOException {
        Transcript transcript = approver.transcript();
        transcript.appendLine("As a greengrocer").
            appendLine("I want to sing");

        String fruit = "banana";
        int fruitCount = 0;

        transcript.appendLine("Given " + fruit + " count " + fruitCount);
        transcript.append("I sing ").appendFormatted(new Song(fruit, fruitCount));
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