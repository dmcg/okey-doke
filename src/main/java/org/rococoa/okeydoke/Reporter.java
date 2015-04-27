package org.rococoa.okeydoke;

public interface Reporter<StorageT> {

    public void reportFailure(StorageT actual, StorageT approved, Throwable e);

}
