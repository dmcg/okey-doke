package org.rococoa.okeydoke;

import java.io.File;

public interface Reporter<T> {

    public void reportFailure(File actual, File approved, Throwable e);

}
