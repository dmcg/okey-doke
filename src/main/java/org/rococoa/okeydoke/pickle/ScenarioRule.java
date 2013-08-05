package org.rococoa.okeydoke.pickle;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.rococoa.okeydoke.Formatter;
import org.rococoa.okeydoke.Transcript;

public class ScenarioRule extends TestWatcher {

    private final Transcript transcript;
    private final int indent = 4;

    public ScenarioRule(Transcript transcript) {
        this.transcript = transcript;
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

    public <T> void thenAssertThat(String s, T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(actual, matcher);
        then(s, actual);
    }

    public void term(String term, Object... os) {
        Transcript transcript = indent().append(term);
        for (int i = 0; i < os.length; i++) {
            Object o = os[i];
            if (o instanceof Formatter)
                appendFormatted(os[i++ + 1], (Formatter<Object, String>) o);
            else
                transcript.space().appendFormatted(o);
        }
        transcript.endl();
    }

    public Transcript appendFormatted(Object o, Formatter<Object, String> formatter) {
        String[] lines = formatter.formatted(o).split("\\n");
        for (int i = 0; i < lines.length; i++) {
            indent().append(lines[i]);
            if (i < lines.length - 1)
                transcript.endl();
        }
        return transcript;
    }

    private Transcript indent() {
        return transcript.space(indent + 4);
    }


    @Override
    protected void starting(Description description) {
        transcript.space(4).endl();
        transcript.space(4).append("Scenario: ").appendLine(scenarioNameFor(description));
    }

    private String scenarioNameFor(Description description) {
        Scenario annotation = description.getAnnotation(Scenario.class);
        return annotation == null ? description.getMethodName() : annotation.value();
    }
}
