#!/bin/bash
set -e

function write_file_contents {
    echo '```java'
    sed -e '1,/README_TEXT/d' -e '/README_TEXT/,$d' $1
    echo '```'
}

echo "
[![Download](https://maven-badges.herokuapp.com/maven-central/com.oneeyedmen/okeydoke/badge.svg?style=flat-square
)](https://search.maven.org/artifact/com.oneeyedmen/okeydoke)

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
"  > README.md

write_file_contents src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsExtensionTest.java >> README.md

echo "
If your source is in src/test/kotlin use [KotlinApprovalsExtension] instead.

## IntelliJ

There is an [IntelliJ plugin](https://github.com/s4nchez/okey-doke-idea) (thanks @s4nchez) to help approve your output.

## JUnit 4

We still support JUnit 4 with a Rule -
[ApprovalsRuleTest](src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsRuleTest.java)
to compare current thing with an approved version and fail with a diff if they aren't the same.
"  >> README.md

write_file_contents src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsRuleTest.java >> README.md


echo "


" >> README.md