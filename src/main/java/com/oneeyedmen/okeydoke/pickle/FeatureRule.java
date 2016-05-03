package com.oneeyedmen.okeydoke.pickle;

import com.oneeyedmen.okeydoke.Transcript;
import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class FeatureRule extends TestWatcher {
    private final FeatureInfo featureInfo;
    private final ApprovalsRule approvalsRule;

    public FeatureRule(FeatureInfo featureInfo, ApprovalsRule approvalsRule) {
        this.featureInfo = featureInfo;
        this.approvalsRule = approvalsRule;
    }

    public ScenarioRule scenarioRule() {
        return new ScenarioRule(approvalsRule.transcript());
    }

    @Override
    protected void starting(Description description) {
        approvalsRule.starting(description);
        writeFeature(approvalsRule.transcript(), featureInfo);
    }

    @Override
    protected void succeeded(Description description) {
        approvalsRule.succeeded(description);
    }

    private void writeFeature(Transcript transcript, FeatureInfo feature) {
        transcript.append("Feature: ").appendLine(feature.name());
        indent(transcript).append("In Order ").appendLine(feature.inOrder());
        indent(transcript).append("As ").appendLine(feature.asA());
        indent(transcript).append("I want ").appendLine(feature.iWant());
    }

    private Transcript indent(Transcript transcript) {
        return transcript.space(4);
    }
}
