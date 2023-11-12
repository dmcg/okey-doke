package com.oneeyedmen.okeydoke.junit4;

import com.oneeyedmen.okeydoke.Name;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.runner.Description.createTestDescription;

public class StandardTestNamerTest {

    private TestNamer namer = new StandardTestNamer();

    @Test
    public void can_use_class_and_method_name() {
        assertThat(namer.nameFor(createTestDescription(this.getClass(), "methodName")),
                equalTo("StandardTestNamerTest.methodName"));
    }

    @Test
    public void can_use_class_name_and_method_name() {
        assertThat(namer.nameFor(createTestDescription("ClassName", "methodName")),
                equalTo("ClassName.methodName"));
    }

    @Test
    public void overrides_with_class_name_annotation() {
        assertThat(namer.nameFor(createTestDescription(TestClass.class, "method")),
                equalTo("Fruit.method"));
    }

    @Test
    public void overrides_with_method_name_annotation() {
        assertThat(namer.nameFor(createTestDescription(TestClass.class, "namedMethod")),
                equalTo("Fruit.banana"));
    }

    @Test
    public void handles_null_method_name() {
        // this seems to be JUnit behaviour
        assertThat(namer.nameFor(createTestDescription(this.getClass(), null)),
                equalTo("StandardTestNamerTest.null"));
    }

    @Name("Fruit")
    static class TestClass {
        public void method() {}
        @Name("banana") public void namedMethod() {}

    }

}

