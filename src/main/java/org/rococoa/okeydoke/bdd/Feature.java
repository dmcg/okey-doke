package org.rococoa.okeydoke.bdd;

import org.rococoa.okeydoke.Transcript;

public class Feature {

    private final Pickle pickle;

    public Feature(Pickle pickle) {
        this.pickle = pickle;
    }

    public Feature inOrder(String s) {
        indent().append("In Order").space().appendLine(s);
        return this;
    }

    public Feature asA(String s) {
        indent().append("As a").space().appendLine(s);
        return this;
    }

    public Feature iWant(String s) {
        indent().append("I want").space().appendLine(s);
        return this;
    }

    private Transcript indent() {
        return pickle.transcript().space(4);
    }

    public Scenario scenario(String description) {
        indent().endl();
        indent().append("Scenario: ").appendLine(description);
        return new Scenario(pickle, 4);
    }
}
