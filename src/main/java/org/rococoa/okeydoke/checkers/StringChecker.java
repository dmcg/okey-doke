package org.rococoa.okeydoke.checkers;

import org.junit.Assert;
import org.rococoa.okeydoke.Checker;

public class StringChecker implements Checker<String> {

    @Override
    public void assertEquals(String expected, String actual) throws AssertionError {
        Assert.assertEquals(expected, actual);
    }
}
