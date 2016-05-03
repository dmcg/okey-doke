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
        writeIfPresent(transcript, "In Order", feature.inOrder());
        writeIfPresent(transcript, "As", feature.asA());
        writeIfPresent(transcript, "I want", feature.iWant());
    }

    private Transcript indent(Transcript transcript) {
        return transcript.space(4);
    }

    private void writeIfPresent(Transcript transcript, String name, String value) {
        if (value.length() > 0)
            indent(transcript).append(name).append(" ").appendLine(value);
    }
}
