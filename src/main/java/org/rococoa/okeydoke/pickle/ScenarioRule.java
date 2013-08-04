package org.rococoa.okeydoke.pickle;

import org.junit.rules.TestWatcher;
import org.rococoa.okeydoke.Transcript;

public class ScenarioRule extends TestWatcher {
    private final Transcript transcript;

    public ScenarioRule(Transcript transcript) {
        this.transcript = transcript;
    }

    @Override
    protected void starting(org.junit.runner.Description description) {
        transcript.space(4).endl();
        transcript.space(4).append("Scenario: ").appendLine(scenarioNameFor(description));
    }

    private String scenarioNameFor(org.junit.runner.Description description) {
        Description annotation = description.getAnnotation(Description.class);
        return annotation == null ? description.getMethodName() : annotation.value();
    }

    public Scenario scenario() {
        return new Scenario(transcript, 4);
    }
}
