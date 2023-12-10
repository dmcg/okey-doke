
[![Download](https://maven-badges.herokuapp.com/maven-central/com.oneeyedmen/okeydoke/badge.svg?style=flat-square
)](https://search.maven.org/artifact/com.oneeyedmen/okeydoke)

okey-doke
=========

An Approval Testing library for Java and JUnit - like [Llewellyn Falco's](http://approvaltests.sourceforge.net/) but more Java'y.

A [helping hand](http://youtu.be/EbqaxWjIgOg) for many testing problems.

## Version 2

If you are upgrading from verions 1.x to version 2.x - JUnit 4 support has been moved from `com.oneeyedmen.junit` to `com.oneeyedmen.junit4`.

In return, you no longer need to specify whether your are using Java or Kotlin

## JUnit 5


```java
public class ApprovalsExtensionTest {

    // Initialise okey-doke.
    @RegisterExtension ApprovalsExtension approvals = new ApprovalsExtension();
        // See other constructors to change where the files are stored,
        // or change the extension

    @Test
    public void something_that_we_want_to_be_the_same_next_time(
            Approver approver // approver will be injected
    ) {
        Object result = doSomeCalculation(42, "banana");
        approver.assertApproved(result); // check that the result is as approved
    }
}
```


The first time you run this test it will fail, but the result of `doSomeCalculation` will  be written into a
file next to the test, named  `ApprovalsExtensionTest.something_that_we_want_to_be_the_same_next_time.actual`

You can look at this file to check that it is what you expect, and if it is, approve the test by renaming the file
it to `ApprovalsExtensionTest.something_that_we_want_to_be_the_same_next_time.approved` (or ask the plugin to do it for you).

From then on the test will pass provided the result of `doSomeCalculation` doesn't change.
If it does change then you can either fix the code if it shouldn't have, or approve the new version.

## IntelliJ

There is an [IntelliJ plugin](https://github.com/s4nchez/okey-doke-idea) (thanks @s4nchez) to help approve your output.

## JUnit 4

We still support JUnit 4 with a Rule -
[ApprovalsRuleTest](src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsRuleTest.java)

```java
    @Rule public final ApprovalsRule approver = ApprovalsRule.usualRule();

    @Test
    public void something_that_we_want_to_be_the_same_next_time(
    ) {
        Object result = doSomeCalculation(42, "banana");
        approver.assertApproved(result); // check that the result is as approved
    }
```

Here you can use the `ApprovalsRule` as a approver.

