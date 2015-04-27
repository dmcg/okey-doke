package org.rococoa.okeydoke;

public interface Checker<ComparedT> {
    public void assertEquals(ComparedT expected, ComparedT actual) throws AssertionError;
}
