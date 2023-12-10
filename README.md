
[![Download](https://maven-badges.herokuapp.com/maven-central/com.oneeyedmen/okeydoke/badge.svg?style=flat-square
)](https://search.maven.org/artifact/com.oneemvnyedmen/okeydoke)

okey-doke
=========

An Approval Testing library for Java and JUnit - like [Llewellyn Falco's](http://approvaltests.sourceforge.net/) but more Java'y.

A [helping hand](http://youtu.be/EbqaxWjIgOg) for many testing problems.

## Breaking Change

If you are upgrading from verions 1.x to version 2.x - JUnit 4 support has been moved from com.oneeyedmen.junit to com.oneeyedmen.junit4

## JUnit 5

Add an @ExtendWith(ApprovalsExtension.class) to your test class to get access to an approver in every test method

[ApprovalsExtensionTest](src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsExtensionTest.java)
to compare current thing with an approved version and fail with a diff if they aren't the same.

```java
@ExtendWith(ApprovalsExtension.class)
public class ApprovalsExtensionTest {


    @Test
    public void doesnt_match_where_no_approved_result(Approver approver) throws IOException {
        whenApprovedIs(null, approver);
        try {
            approver.assertApproved("banana");
            fail("should have thrown");
        } catch (AssertionError expected) {
        }
    }

    @Test
    public void matches_when_approved_result_matches(Approver approver) throws IOException {
        whenApprovedIs("banana", approver);
        approver.assertApproved("banana");
    }

    @Test
    public void doesnt_match_when_approved_result_doesnt_match(Approver approver) throws IOException {
        whenApprovedIs("banana", approver);
        try {
            approver.assertApproved("kumquat");
            fail("should have thrown");
        } catch (AssertionFailedError expected) {
            assertEquals("kumquat", expected.getActual().getValue());
            assertEquals("banana", expected.getExpected().getValue());
        }
    }

```

If your source is in src/test/kotlin use [KotlinApprovalsExtension] instead.

## IntelliJ

There is an [IntelliJ plugin](https://github.com/s4nchez/okey-doke-idea) (thanks @s4nchez) to help approve your output.

## JUnit 4

We still support JUnit 4 with a Rule -
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
        } catch (AssertionFailedError expected) {
            assertEquals("kumquat", expected.getActual().getValue());
            assertEquals("banana", expected.getExpected().getValue());
        }
    }

```




