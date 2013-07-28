package org.rococoa.okeydoke;

/**
 * The transcript provides a way to write text into the approved output.
 */
public interface Transcript {

    public Transcript appendLine(String s);

    public Transcript append(String s);

    public Transcript appendFormatted(Object o);

    public <T> Transcript appendFormatted(T o, Formatter<T,String> formatter);

    public Transcript endl();

    public Transcript space();

    public Transcript space(int number);
}
