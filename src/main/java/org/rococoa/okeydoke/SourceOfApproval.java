package org.rococoa.okeydoke;

import java.io.IOException;

public interface SourceOfApproval {

    public void writeApproved(String testname, byte[] bytes) throws IOException;

    public String readApproved(String testname);

    public void writeActual(String testname, byte[] bytes);

    public String toApproveText(String testname);
}
