okey-doke
=========

An Approval Testing library for Java and JUnit - like [Llewellyn Falco's](http://approvaltests.sourceforge.net/) but more Java'y
and more the SimplestThingThatCouldPossiblyWork. A [helping hand](http://youtu.be/EbqaxWjIgOg) for many testing problems.


There are four modes of operation, exemplified by

1 [ApprovalsRuleTest](src/test/java/com/oneeyedmen/okeydoke/examples/ApprovalsRuleTest.java)
 - compare current thing with an approved version and fail with a diff if they aren't the same.

2 [LockDownTest](src/test/java/com/oneeyedmen/okeydoke/examples/LockDownTest.java)
 - for easy testing of legacy code.

3 [TranscriptTest](src/test/java/com/oneeyedmen/okeydoke/examples/TranscriptTest.java)
 - for producing approved files with interactions.

4 [PickleTest](src/test/java/com/oneeyedmen/okeydoke/examples/PickleTest.java) and [PickleTableTest](src/test/java/com/oneeyedmen/okeydoke/examples/PickleTablesTest.java)
 - for producing Gerkin-like output from your tests that the customer can approve.
