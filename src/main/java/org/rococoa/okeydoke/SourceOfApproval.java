package org.rococoa.okeydoke;

import java.io.IOException;

public interface SourceOfApproval {

    public void writeApproved(String testname, byte[] bytes) throws IOException;

    public byte[] readApproved(String testname);

    public String toApproveText(String testname);

    CompareResult writeAndCompare(String testname, byte[] actualAsBytes);

    public class CompareResult {
        public final AssertionError errorOrNull;
        public final byte[] approvedOrNull;

        public CompareResult(AssertionError errorOrNull, byte[] approvedOrNull) {
            this.errorOrNull = errorOrNull;
            this.approvedOrNull = approvedOrNull;
        }
    }
}
