package org.rococoa.okeydoke;

import java.io.PrintStream;

public class StandardTranscript implements Transcript {

    private final PrintStream stream;
    private final Formatter<Object, String> formatter;

    private boolean isStartOfLine = true;

    public StandardTranscript(PrintStream stream, Formatter<Object, String> formatter) {
        this.stream = stream;
        this.formatter = formatter;
    }

    @Override public boolean isStartOfLine() {
        return isStartOfLine;
    }

    @Override
    public Transcript appendLine(String s) {
        stream.println(s);
        isStartOfLine = true;
        return this;
    }

    @Override
    public Transcript append(String s) {
        stream.append(s);
        isStartOfLine = s.endsWith("\n");
        return this;
    }

    @Override
    public Transcript appendFormatted(Object o) {
        appendFormatted(o, formatter);
        return this;
    }

    @Override
    public <T> Transcript appendFormatted(T o, Formatter<T, String> aFormatter) {
        append(aFormatter.formatted(o));
        return this;
    }

    @Override
    public Transcript endl() {
        stream.println();
        isStartOfLine = true;
        return this;
    }

    @Override
    public Transcript space() {
        return append(" ");
    }

    @Override
    public Transcript space(int number) {
        for (int i = 0; i < number; i++) {
            space();
        }
        return this;
    }
}
