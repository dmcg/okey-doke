package com.oneeyedmen.okeydoke;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class StandardTranscriptTest {

    private final OutputStream outputStream = new ByteArrayOutputStream();

    @Test
    public void doesnt_use_platform_line_ending() {
        String oldSeparator = System.getProperty("line.separator");

        try {
            System.setProperty("line.separator", "***\n");
            Transcript transcript = new StandardTranscript(new PrintStream(outputStream), Formatters.stringFormatter());
            transcript.appendLine("Line 1").append("Line 2").endl();
        } finally {
            System.setProperty("line.separator", oldSeparator);
        }

        assertEquals("Line 1\nLine 2\n", outputStream.toString());
    }

    @Test
    public void uses_given_line_ending() {
        Transcript transcript = new StandardTranscript(new PrintStream(outputStream), Formatters.stringFormatter(), "$");
        transcript.appendLine("Line 1").append("Line 2").endl();
        assertEquals("Line 1$Line 2$", outputStream.toString());
    }

}
