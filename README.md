
okey-doke
=========

An Approval Testing library for Java and JUnit - like [Llewellyn Falco's](http://approvaltests.sourceforge.net/) but more Java'y.

A [helping hand](http://youtu.be/EbqaxWjIgOg) for many testing problems.

The basic mode of operation is
[ApprovalsRuleTest](src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsRuleTest.java)
to compare current thing with an approved version and fail with a diff if they aren't the same.

```java

    @Rule public final ApprovalsRule approver = ApprovalsRule.usualRule();

    @Test
    public void doesnt_match_where_no_approved_result() throws IOException {
        whenApprovedIs(null);
        try {
            approver.assertApproved("banana");
            fail("should have thrown");
        } catch (AssertionError expected) {
        }
    }

    @Test
    public void matches_when_approved_result_matches() throws IOException {
        whenApprovedIs("banana");
        approver.assertApproved("banana");
    }

    @Test
    public void doesnt_match_when_approved_result_doesnt_match() throws IOException {
        whenApprovedIs("banana");
        try {
            approver.assertApproved("kumquat");
            fail("should have thrown");
        } catch (ComparisonFailure expected) {
            assertEquals("kumquat", expected.getActual());
            assertEquals("banana", expected.getExpected());
        }
    }

```

Next try [LockDownTest](src/test/java/com/oneeyedmen/okeydoke/examples/LockDownTest.java)
for easy testing of legacy code.

```java

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

```

Then move on to
- [TranscriptTest](src/test/java/com/oneeyedmen/okeydoke/examples/TranscriptTest.java)
for producing approved files with interactions
- [PickleTest](src/test/java/com/oneeyedmen/okeydoke/examples/PickleTest.java) and [PickleTableTest](src/test/java/com/oneeyedmen/okeydoke/examples/PickleTablesTest.java)
for producing Gerkin-like output from your tests that the customer can approve.

Oh, and I should mention, there is an [IntelliJ plugin](https://github.com/s4nchez/okey-doke-idea) (thanks Ivan) to help approve your output.

