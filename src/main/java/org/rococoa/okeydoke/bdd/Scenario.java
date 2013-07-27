package org.rococoa.okeydoke.bdd;

import org.rococoa.okeydoke.Transcript;

public class Scenario {

    private final Pickle pickle;

    public Scenario(Pickle pickle) {
        this.pickle = pickle;
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
        transcript.append(term);
        for (Object o : os) {
            transcript.space().appendFormatted(o);
        }
        transcript.endl();
    }

}
