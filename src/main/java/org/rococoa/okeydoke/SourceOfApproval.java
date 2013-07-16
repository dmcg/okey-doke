package org.rococoa.okeydoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @param <F> - the type of the storage of approved and actual
 */
public interface SourceOfApproval<F> {

    public OutputStream outputForApproved(String testname) throws IOException;

    public OutputStream outputForActual(String testname) throws IOException;

    public InputStream inputOrNullForApproved(String testname) throws IOException;

    public InputStream inputOrNullForActual(String testname) throws IOException;

    public void removeActual(String testname) throws IOException;

    public F approvedFor(String testname);

    public F actualFor(String testname);
}
