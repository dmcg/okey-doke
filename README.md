okey-doke
=========

An Approval Testing library for Java and JUnit - like http://approvaltests.sourceforge.net/ but more Java'y
and more the SimplestThingThatCouldPossiblyWork. A [helping hand](http://youtu.be/EbqaxWjIgOg) for many testing problems.


There are three modes of operation, exemplified by

1. [ApprovalsRuleTest](https://github.com/dmcg/okey-doke/blob/master/src/test/java/org/rococoa/okeydoke/examples/ApprovalsRuleTest.java)
 - compare current thing with an approved version and fail with a diff if they aren't the same.
2. [LockDownTest](https://github.com/dmcg/okey-doke/blob/master/src/test/java/org/rococoa/okeydoke/examples/LockDownTest.java)
 - for easy testing of legacy code.
3. [TranscriptTest](https://github.com/dmcg/okey-doke/blob/master/src/test/java/org/rococoa/okeydoke/examples/TranscriptTest.java)
 - for producing approved files with interactions.
4. [PickleTest](https://github.com/dmcg/okey-doke/blob/master/src/test/java/org/rococoa/okeydoke/examples/PickleTest.java)
 - for producing Gerkin-like output from your tests that the customer can approve.

I'll get round to publishing to Maven Central eventually, but in the meantime there are relatively up to date jar files
[here](http://oneeyedmen.com/okeydoke).

