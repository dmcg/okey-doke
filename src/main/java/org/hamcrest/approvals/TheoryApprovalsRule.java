package org.hamcrest.approvals;

import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class TheoryApprovalsRule extends ApprovalsRule {

    private List<Object[]> results = new ArrayList<Object[]>();

    public TheoryApprovalsRule(String sourceRoot) {
        super(sourceRoot);
    }

    public void lockDown(Object result, Object... arguments) {
        results.add(new Object[] {result, arguments});
    }

    @Override
    protected void succeeded(Description description) {
        assertThat(formatted(results), isAsApproved());
    }

    private String formatted(List<Object[]> results) {
        StringBuilder result = new StringBuilder();
        for (Object[] each: results) {
            result.append("[").append(formatted((Object[]) each[1])).append("] -> ");
            result.append(String.valueOf(each[0])).append("\n");
        }
        return result.toString();
    }

    private String formatted(Object[] parameters) {
        StringBuilder result = new StringBuilder();
        for (Object parameter : parameters) {
            result.append(String.valueOf(parameter)).append(", ");
        }
        return result.substring(0, result.length() - 2).toString();
    }

}
