package org.rococoa.okeydoke.pickle;

import org.junit.rules.TestWatcher;
import org.rococoa.okeydoke.Transcript;
import org.rococoa.okeydoke.junit.ApprovalsRule;

public class FeatureRule extends TestWatcher {
    private final ApprovalsRule approvalsRule;

    public FeatureRule(ApprovalsRule approvalsRule) {
        this.approvalsRule = approvalsRule;
    }

    public ScenarioRule scenarioRule() {
        return new ScenarioRule(approvalsRule.transcript());
    }

    @Override
    protected void starting(org.junit.runner.Description description) {
        Description featureAnnotation = description.getTestClass().getAnnotation(Description.class);
        writeFeature(approvalsRule.transcript(), featureAnnotation);
    }

    private void writeFeature(Transcript transcript, Description description) {
        transcript.append("Feature: ").appendLine(description.value());
        transcript.space(4).append("In Order ").appendLine(description.inOrder());
        transcript.space(4).append("As ").appendLine(description.as());
        transcript.space(4).append("I want ").appendLine(description.iWant());
    }
}
