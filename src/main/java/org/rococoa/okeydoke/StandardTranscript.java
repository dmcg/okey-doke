package org.rococoa.okeydoke;

import java.io.PrintStream;

public class StandardTranscript implements Transcript {

    private final PrintStream stream;
    private final Formatter<Object, String> formatter;

    public StandardTranscript(PrintStream stream, Formatter<Object, String> formatter) {
        this.stream = stream;
        this.formatter = formatter;
    }

    @Override
    public Transcript appendLine(String s) {
        stream.println(s);
        return this;
    }

    @Override
    public Transcript append(String s) {
        stream.append(s);
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
        return this;
    }
}
