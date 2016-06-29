package com.oneeyedmen.okeydoke;

public interface Reporter<ApprovedStorageT, ActualStorageT> {

    public void reportFailure(ApprovedStorageT actual, ActualStorageT approved, Throwable e);

}
