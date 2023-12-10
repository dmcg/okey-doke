package com.oneeyedmen.okeydoke.junit5;

import com.oneeyedmen.okeydoke.Approver;
import com.oneeyedmen.okeydoke.ApproverFactory;
import com.oneeyedmen.okeydoke.Name;
import com.oneeyedmen.okeydoke.util.DirectoryFinder;
import org.junit.jupiter.api.extension.*;

import java.io.File;
import java.lang.reflect.Method;

import static com.oneeyedmen.okeydoke.ApproverFactories.fileSystemApproverFactory;

/**
 * A JUnit 5 Extension to provide an Approver as a parameter to test functions.
 * <br>
 * Use as a class in `@ExtendWith(ApprovalsExtension.class)`
 * <br>
 * or a field
 * <br>
 * `@RegisterExtension ApprovalsExtension approvals = new ApprovalsExtension();`
 */
public class ApprovalsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback, ParameterResolver {

    private static final String STORE_KEY = "okeydoke.approver";

    private final ApproverFactory<Approver> factory;
    private final TestNamer testNamer = new TestNamer();

    public ApprovalsExtension(ApproverFactory<Approver> factory) {
        this.factory = factory;
    }

    public ApprovalsExtension(File sourceRoot) {
        this(fileSystemApproverFactory(sourceRoot));
    }

    public ApprovalsExtension(File sourceRoot, String extension) {
        this(fileSystemApproverFactory(sourceRoot, extension));
    }

    public ApprovalsExtension(String extension) {
        this(fileSystemApproverFactory(findARootDirectory(), extension));
    }

    public ApprovalsExtension() {
        this(findARootDirectory());
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        store(context).put(STORE_KEY, factory.createApprover(
                testNamer.nameFor(context.getRequiredTestClass(), context.getRequiredTestMethod()),
                context.getRequiredTestClass()));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (!context.getExecutionException().isPresent()) {
            Approver approver = (Approver) store(context).get(STORE_KEY);
            if (!approver.satisfactionChecked()) {
                approver.assertSatisfied();
            }
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return Approver.class.equals(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (Approver.class.equals(parameterContext.getParameter().getType())) {
            return store(extensionContext).get(STORE_KEY);
        }
        return null;
    }

    private ExtensionContext.Store store(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(context.getRequiredTestClass().getName(), context.getRequiredTestMethod().getName()));
    }

    private static class TestNamer {
        public String nameFor(Class<?> testClass, Method testMethod) {
            return nameFromClass(testClass) + "." + nameFromMethod(testMethod);
        }

        private String nameFromMethod(Method testMethod) {
            Name nameAnnotation = testMethod.getAnnotation(Name.class);
            if (nameAnnotation != null) {
                return nameAnnotation.value();
            }
            return testMethod.getName();
        }

        private String nameFromClass(Class<?> testClass) {
            Name nameAnnotation = testClass.getAnnotation(Name.class);
            if (nameAnnotation != null) {
                return nameAnnotation.value();
            }
            return testClass.getSimpleName();
        }
    }

    private static final File[] likelyDirectories = {
            new File("src/test/java"),
            new File("src/test/kotlin"),
            new File("src/test/scala")
    };

    private static File findARootDirectory() {
        File firstDirThatExists = DirectoryFinder.firstDirThatExists(likelyDirectories);
        if (firstDirThatExists == null) {
            throw new IllegalStateException("Couldn't find a source directory");
        }
        return firstDirThatExists;
    }
}
