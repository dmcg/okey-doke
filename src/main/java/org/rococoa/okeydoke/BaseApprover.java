package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.IO;
import org.rococoa.okeydoke.sources.Snitch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.rococoa.okeydoke.internal.IO.closeQuietly;

public class BaseApprover<T, C, F> {

    protected final String testName;
    protected final SourceOfApproval<F> sourceOfApproval;
    private final Formatter<T, C> formatter;
    private final Reporter<F> reporter;

    private OutputStream osForActual;
    private boolean done;

    protected BaseApprover(String testName, SourceOfApproval<F> sourceOfApproval,
                           Formatter<T, C> formatter,
                           Reporter<F> reporter) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
        this.formatter = formatter;
        this.reporter = reporter;
    }

    public PrintStream printStream() throws IOException {
        return new PrintStream(osForActual());
    }

    public void writeFormatted(T object) throws IOException {
        writeFormatted(object, formatter);
    }

    public void writeFormatted(T object, Formatter<T, C> aFormatter) throws IOException {
        aFormatter.writeTo(aFormatter.formatted(object), osForActual());
    }

    public void assertApproved(T actual) throws IOException {
        assertApproved(actual, formatter);
    }

    public void assertApproved(T actual, Formatter<T, C> aFormatter) throws IOException {
        writeFormatted(actual, aFormatter);
        assertSatisfied();
    }

    public void assertSatisfied() throws IOException {
        try {
            osForActual().close();
            try {
                if (osForActual() instanceof Snitch) {
                    ((Snitch) osForActual()).grassOnTransgressions();
                } else {
                    checkByReading();
                }
                sourceOfApproval.removeActual(testName);
            } catch (AssertionError e) {
                reporter.reportFailure(
                        sourceOfApproval.actualFor(testName),
                        sourceOfApproval.approvedFor(testName),
                        e);
                throw e;
            }
        } finally {
            osForActual = null;
            done = true;
        }
    }

    private void checkByReading() throws IOException {
        try {
            formatter.assertEquals(formatter.readFrom(inputForApproved()), formatter.readFrom(inputForActual()));
        } finally {
            IO.closeQuietly(inputForActual());
            IO.closeQuietly(inputForApproved());
        }
    }

    private InputStream inputForActual() throws IOException {
        InputStream result = sourceOfApproval.inputOrNullForActual(testName);
        if (result != null) {
            return result;
        }
        throw new AssertionError("This is embarrassing - I've lost the 'actual' I just wrote for " + testName);
            // Can happen if we have opened file early and then delete the directory
    }

    private InputStream inputForApproved() throws IOException {
        InputStream existing = sourceOfApproval.inputOrNullForApproved(testName);
        if (existing != null)
            return existing;
        approve(formatter.emptyThing());
        return sourceOfApproval.inputOrNullForApproved(testName);

    }

    public void approve(T approved) throws IOException {
        OutputStream output = sourceOfApproval.outputForApproved(testName);
        try {
            formatter.writeTo(formatter.formatted(approved), output);
        } finally {
            closeQuietly(output);
        }
    }

    public boolean satisfactionChecked() {
        return done;
    }

    public Formatter<T, C> formatter() {
        return formatter;
    }

    protected OutputStream osForActual() throws IOException {
        if (osForActual == null)
            osForActual = sourceOfApproval.outputForActual(testName);
        return osForActual;
    }

    public C readActual() throws IOException {
        InputStream inputForActualOrNull = sourceOfApproval.inputOrNullForActual(testName);
        try {
            if (inputForActualOrNull == null)
                throw new AssertionError("This is embarrassing - I've lost the 'actual' I just wrote for " + testName);
            return formatter.readFrom(inputForActualOrNull);
        } finally {
            IO.closeQuietly(inputForActualOrNull);
        }
    }


}
