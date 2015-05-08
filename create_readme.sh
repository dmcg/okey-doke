#!/bin/bash
set -e

function write_file_contents {
    echo '```java'
    sed -e '1,/README_TEXT/d' -e '/README_TEXT/,$d' $1
    echo '```'
}

echo "
okey-doke
=========

An Approval Testing library for Java and JUnit - like [Llewellyn Falco's](http://approvaltests.sourceforge.net/) but more Java'y.

A [helping hand](http://youtu.be/EbqaxWjIgOg) for many testing problems.

The basic mode of operation is
[ApprovalsRuleTest](src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsRuleTest.java)
to compare current thing with an approved version and fail with a diff if they aren't the same.
"  > README.md

write_file_contents src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsRuleTest.java >> README.md

echo "
Next try [LockDownTest](src/test/java/com/oneeyedmen/okeydoke/examples/LockDownTest.java)
for easy testing of legacy code.
"  >> README.md

write_file_contents src/test/java/com/oneeyedmen/okeydoke/examples/LockDownTest.java >> README.md

echo "
Then move on to
- [TranscriptTest](src/test/java/com/oneeyedmen/okeydoke/examples/TranscriptTest.java)
for producing approved files with interactions
- [PickleTest](src/test/java/com/oneeyedmen/okeydoke/examples/PickleTest.java) and [PickleTableTest](src/test/java/com/oneeyedmen/okeydoke/examples/PickleTablesTest.java)
for producing Gerkin-like output from your tests that the customer can approve.
" >> README.md