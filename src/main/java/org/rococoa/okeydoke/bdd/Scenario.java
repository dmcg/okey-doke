package org.rococoa.okeydoke.bdd;

public class Scenario {

    private final Pickle pickle;

    public Scenario(Pickle pickle) {
        this.pickle = pickle;
    }

    public void given(String s) {
        term("Given", s);
    }

    public void and(String s) {
        term("And", s);
    }

    public void when(String s) {
        term("When", s);
    }

    public void then(String s, Object o) {
        pickle.transcript().append("Then").space().append(s).space().appendFormatted(o).endl();
    }

    public void term(String term, String s) {
        pickle.transcript().append(term).space().appendLine(s);
    }
}
