package com.oneeyedmen.okeydoke;

public interface Reporter<StorageT> {

    public void reportFailure(StorageT actual, StorageT approved, Throwable e);

}
