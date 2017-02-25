package com.oneeyedmen.okeydoke.junit;

import org.junit.runner.Description;

public interface TestNamer {

    public String nameFor(Description description);
}
