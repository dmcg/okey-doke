package org.rococoa.okeydoke.bdd;

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
        Transcript transcript = pickle.transcript();
        indent().append(term);
        for (Object o : os) {
            transcript.space().appendFormatted(o);
        }
        transcript.endl();
    }

    private Transcript indent() {
        return pickle.transcript().space(indent + 4);
    }
}
