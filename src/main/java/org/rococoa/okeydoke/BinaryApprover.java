package org.rococoa.okeydoke;

import java.io.File;

/**
 * A BinaryApprover is used to write raw bytes to file.
 */
public class BinaryApprover extends BaseApprover<byte[], byte[], File> {

    public BinaryApprover(String testname, SourceOfApproval<File> sourceOfApproval, Reporter<File> reporter) {
        super(testname, sourceOfApproval, Formatters.binaryFormatter(), Serializers.binarySerializer(), reporter);
    }
}
