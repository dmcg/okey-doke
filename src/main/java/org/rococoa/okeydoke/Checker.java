package org.rococoa.okeydoke;

public interface Checker<C> {
    public void assertEquals(C expected, C actual) throws AssertionError;
}
