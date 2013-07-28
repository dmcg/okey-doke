package org.rococoa.okeydoke.bdd;

import org.rococoa.okeydoke.Transcript;

public class Pickle {

    private final Transcript transcript;

    public Pickle(Transcript transcript) {
        this.transcript = transcript;
    }

    public Transcript transcript() {
        return transcript;
    }

    public Feature feature(String description) {
        transcript().append("Feature: ").appendLine(description);
        return new Feature(this);
    }

    public Scenario scenario(String description) {
        transcript().append("Scenario: ").appendLine(description);
        return new Scenario(this, 0);
    }
}
