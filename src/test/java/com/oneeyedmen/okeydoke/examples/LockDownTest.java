package com.oneeyedmen.okeydoke.examples;

import com.oneeyedmen.okeydoke.junit4.TheoryApprovalsRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

import static com.oneeyedmen.okeydoke.junit4.TheoryApprovalsRule.fileSystemRule;

/**
 * Here we show how to use Theories and DataPoints to push all combinations of parameters into an approver
 * and then lockDown the results.
 */
@RunWith(Theories.class)
public class LockDownTest {

    //README_TEXT

    @ClassRule public static final TheoryApprovalsRule theoryRule = fileSystemRule("src/test/java");
    @Rule public final TheoryApprovalsRule.TheoryApprover approver = theoryRule.approver();

    @DataPoints public static final Fruit[] FRUITS = Fruit.values();
    @DataPoints public static final Animal[] ANIMALS = Animal.values();
    @DataPoints public static final int[] INTS = { -1, 0, 1, 2, 42 };

    @Theory
    public void legacyMethod_checked_reflectively(Fruit fruit, Animal animal, int  i) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        approver.lockDownReflectively(this, "legacyMethod", fruit.name(), animal.name(), i);
    }

    @Theory
    public void legacyMethod_checked_fluently(Fruit fruit, Animal animal, int  i) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        approver.lockDown(this).legacyMethod(fruit.name(), animal.name(), i);
    }

    public String legacyMethod(String fruitName, String animalName, int i) {
        return String.format("%s %s eating %ss", i, fruitName, animalName);
    }

    private static enum Fruit {
        apple, banana, kumquat
    }

    private static enum Animal {
        bear, cat, dog
    }

    //README_TEXT
}
