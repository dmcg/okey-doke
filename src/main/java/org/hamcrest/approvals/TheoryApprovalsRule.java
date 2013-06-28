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

    public TheoryApprover approver() {
        return new TheoryApprover(sourceRoot);
    }

    @Override
    protected void succeeded(Description description) {
        assertThat(formatted(results), isAsApproved());
    }

    private String formatted(List<Object[]> results) {
        StringBuilder result = new StringBuilder();
        for (Object[] each: results) {
            result.append(each[0]).append(" : ");
            result.append("[").append(formatted((Object[]) each[2])).append("] -> ");
            result.append(String.valueOf(each[1])).append("\n");
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

    public class TheoryApprover extends TestRememberer {

        private Description theory;

        public TheoryApprover(String sourceRoot) {
            super(sourceRoot);
        }

        @Override
        public void starting(Description description) {
            theory = description;
            super.starting(description);
        }

        public void lockDown(Object result, Object... arguments) {
            results.add(new Object[] {theory, result, arguments});
        }


    }
}
