package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SourceOfApproval {

    public OutputStream outputForApproved(String testname) throws IOException;

    public OutputStream outputForActual(String testname) throws IOException;

    public InputStream inputOrNullForApproved(String testname) throws IOException;

    public InputStream inputOrNullForActual(String testname) throws IOException;

    public String toApproveText(String testname);


}
