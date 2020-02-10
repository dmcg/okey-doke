package com.oneeyedmen.okeydoke.junit5;

import com.oneeyedmen.okeydoke.Approver;
import com.oneeyedmen.okeydoke.ApproverFactories;
import com.oneeyedmen.okeydoke.ApproverFactory;
import com.oneeyedmen.okeydoke.Name;
import org.junit.jupiter.api.extension.*;

import java.io.File;
import java.lang.reflect.Method;

public class ApprovalsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback, ParameterResolver {

    private static final String STORE_KEY = "approver";

    private final ApproverFactory<Approver> factory = ApproverFactories.fileSystemApproverFactory(new File("src/test/resources"));
    private final TestNamer testNamer = new TestNamer();

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        store(context).put(STORE_KEY, factory.createApprover(
                testNamer.nameFor(context.getRequiredTestClass(), context.getRequiredTestMethod()),
                context.getRequiredTestClass()));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
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
}
