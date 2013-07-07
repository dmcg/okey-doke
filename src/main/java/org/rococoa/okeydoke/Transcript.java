package org.rococoa.okeydoke;

public interface Transcript {

    public Transcript appendLine(String s);

    public Transcript append(String s);

    public Transcript appendFormatted(Object o);

    public Transcript appendFormatted(Object o, Formatter<String> formatter);

    public Transcript endl();
}
