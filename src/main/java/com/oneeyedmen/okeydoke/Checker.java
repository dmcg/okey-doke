package com.oneeyedmen.okeydoke;

public interface Checker<ComparedT> {
    public void assertEquals(ComparedT expectedMayBeNull, ComparedT actualMayBeNull) throws AssertionError;
}
