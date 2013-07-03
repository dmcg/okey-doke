package org.rococoa.okeydoke;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

@RunWith(Theories.class)
public class QuickCheckTest {

    @ClassRule public static final TheoryApprovalsRule theoryRule = TheoryApprovalsRule.fileSystemRule("src/test/java", "target");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @DataPoints public static final Fruit[] FRUITs = Fruit.values();
    @DataPoints public static final Animal[] ANIMALS = Animal.values();
    @DataPoints public static final int[] INTS = { -1, 0, 1, 2, 42 };

    @Theory
    public void legacyMethod_output(Fruit fruit, Animal animal, int  i) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        approver.lockDownReflectively(this, "legacyMethod", fruit.name(), animal.name(), i);
    }

    public String legacyMethod(String fruitName, String animalName, int i) {
        return String.format("%s %s eating %ss", i, fruitName, animalName);
    }

    private static enum Fruit {
        apple, banana, kumquat;
    }

    private static enum Animal {
        bear, cat, dog;
    }
}
