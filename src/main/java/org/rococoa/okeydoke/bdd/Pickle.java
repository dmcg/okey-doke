package org.rococoa.okeydoke.bdd;

import org.rococoa.okeydoke.Transcript;

public class Pickle {

    private final Transcript transcript;

    public Pickle(Transcript transcript) {
        this.transcript = transcript;
    }

    public Scenario scenario(String description) {
        transcript.appendLine("Scenario: " + description);
        return new Scenario(this);
    }

    public Transcript transcript() {
        return transcript;
    }
}
