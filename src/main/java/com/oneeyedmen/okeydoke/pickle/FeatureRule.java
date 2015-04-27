package com.oneeyedmen.okeydoke.pickle;

import com.oneeyedmen.okeydoke.Transcript;
import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class FeatureRule extends TestWatcher {
    private final ApprovalsRule approvalsRule;

    public FeatureRule(ApprovalsRule approvalsRule) {
        this.approvalsRule = approvalsRule;
    }

    public ScenarioRule scenarioRule() {
        return new ScenarioRule(approvalsRule.transcript());
    }

    @Override
    protected void starting(Description description) {
        approvalsRule.starting(description);
        Feature featureAnnotation = description.getTestClass().getAnnotation(Feature.class);
        writeFeature(approvalsRule.transcript(), featureAnnotation);
    }

    @Override
    protected void succeeded(Description description) {
        approvalsRule.succeeded(description);
    }

    private void writeFeature(Transcript transcript, Feature feature) {
        transcript.append("Feature: ").appendLine(feature.value());
        indent(transcript).append("In Order ").appendLine(feature.inOrder());
        indent(transcript).append("As ").appendLine(feature.as());
        indent(transcript).append("I want ").appendLine(feature.iWant());
    }

    private Transcript indent(Transcript transcript) {
        return transcript.space(4);
    }
}
