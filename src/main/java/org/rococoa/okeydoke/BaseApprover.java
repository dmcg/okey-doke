package org.rococoa.okeydoke;

import org.rococoa.okeydoke.internal.IO;
import org.rococoa.okeydoke.sources.Snitch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.rococoa.okeydoke.internal.IO.closeQuietly;

public class BaseApprover<T, C, F> {

    private final String testName;
    private final SourceOfApproval<F> sourceOfApproval;
    private final Formatter<T, C> formatter;
    private final Serializer<C> serializer;
    private final Checker<C> checker;
    private final Reporter<F> reporter;

    private OutputStream osForActual;
    private boolean done;

    protected BaseApprover(String testName, SourceOfApproval<F> sourceOfApproval,
                           Formatter<T, C> formatter,
                           Serializer<C> serializer,
                           Checker<C> checker,
                           Reporter<F> reporter) {
        this.testName = testName;
        this.sourceOfApproval = sourceOfApproval;
        this.formatter = formatter;
        this.serializer = serializer;
        this.checker = checker;
        this.reporter = reporter;
    }

    public PrintStream printStream() {
        try {
            return new PrintStream(osForActual());
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public void writeFormatted(T object) {
        writeFormatted(object, formatter);
    }

    public <T2 extends T> void writeFormatted(T2 object, Formatter<T2, C> aFormatter) {
        try {
            serializer.writeTo(aFormatter.formatted(object), osForActual());
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public void assertApproved(T actual) {
        assertApproved(actual, formatter);
    }

    public <T2 extends T> void assertApproved(T2 actual, Formatter<T2, C> aFormatter) {
        writeFormatted(actual, aFormatter);
        assertSatisfied();
    }

    public void assertSatisfied() {
        try {
            osForActual().close();
            try {
                if (osForActual() instanceof Snitch) {
                    ((Snitch) osForActual()).grassOnTransgressions();
                } else {
                    checkByReading();
                }
                sourceOfApproval.removeActual(testName());
            } catch (AssertionError e) {
                reporter.reportFailure(
                        sourceOfApproval.actualFor(testName()),
                        sourceOfApproval.approvedFor(testName()),
                        e);
                throw e;
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            osForActual = null;
            done = true;
        }
    }

    private void checkByReading() throws IOException {
        InputStream approved = inputForApproved();
        InputStream actual = inputForActual();
        try {
            checker.assertEquals(serializer.readFrom(approved), serializer.readFrom(actual));
        } finally {
            IO.closeQuietly(actual);
            IO.closeQuietly(approved);
        }
    }

    private InputStream inputForActual() throws IOException {
        InputStream result = sourceOfApproval.inputOrNullForActual(testName());
        if (result != null) {
            return result;
        }
        throw new AssertionError("This is embarrassing - I've lost the 'actual' I just wrote for " + testName());
            // Can happen if we have opened file early and then delete the directory
    }

    private InputStream inputForApproved() throws IOException {
        InputStream existing = sourceOfApproval.inputOrNullForApproved(testName());
        if (existing != null)
            return existing;
        writeToApproved(serializer.emptyThing());
        return sourceOfApproval.inputOrNullForApproved(testName());
    }

    public void approve(T approved) {
        writeToApproved(formatter.formatted(approved));
    }

    private void writeToApproved(C formatted) {
        try {
            OutputStream output = sourceOfApproval.outputForApproved(testName());
            try {
                serializer.writeTo(formatted, output);
            } finally {
                closeQuietly(output);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
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
            osForActual = sourceOfApproval.outputForActual(testName());
        return osForActual;
    }

    public C readActual() {
        try {
            InputStream inputForActualOrNull = sourceOfApproval.inputOrNullForActual(testName());
            try {
                if (inputForActualOrNull == null)
                    throw new AssertionError("This is embarrassing - I've lost the 'actual' I just wrote for " + testName());
                return serializer.readFrom(inputForActualOrNull);
            } finally {
                IO.closeQuietly(inputForActualOrNull);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    protected String testName() {
        return testName;
    }
}
