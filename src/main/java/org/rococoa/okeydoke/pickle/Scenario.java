package org.rococoa.okeydoke.pickle;

import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.Transcript;

public class Scenario {

    private final Pickle pickle;
    private final int indent;

    public Scenario(Pickle pickle, int indent) {
        this.pickle = pickle;
        this.indent = indent;
    }

    public void given(Object... os) {
        term("Given", os);
    }

    public void and(Object... os) {
        term("And", os);
    }

    public void when(Object... os) {
        term("When", os);
    }

    public void then(Object... os) {
        term("Then", os);
    }

    public void term(String term, Object... os) {
        Transcript transcript = indent().append(term);
        for (Object o : os) {
            transcript.space().appendFormatted(o);
        }
        transcript.endl();
    }

    public Transcript appendFormatted(Object o, Formatter<Object, String> formatter) {
        String[] lines = formatter.formatted(o).split("\\n");
        for (String line : lines) {
            indent().append(line).endl();
        }
        return transcript();
    }

    private Transcript transcript() {
        return pickle.transcript();
    }

    private Transcript indent() {
        return transcript().space(indent + 4);
    }
}
