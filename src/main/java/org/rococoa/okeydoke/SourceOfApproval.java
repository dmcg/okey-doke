package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SourceOfApproval {

    public OutputStream approvedOutputFor(String testname) throws IOException;

    public OutputStream actualOutputFor(String testname) throws IOException;

    public InputStream approvedInputOrNullFor(String testname) throws IOException;

    public String toApproveText(String testname);


}
