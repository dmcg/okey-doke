package com.oneeyedmen.okeydoke.checkers;

import com.oneeyedmen.okeydoke.Checker;
import org.junit.Assert;

public class StringChecker implements Checker<String> {

    @Override
    public void assertEquals(String expected, String actual) throws AssertionError {
        Assert.assertEquals(expected, actual);
    }
}
