package org.rococoa.okeydoke;

public interface Reporter<F> {

    public void reportFailure(F actual, F approved, Throwable e);

}
