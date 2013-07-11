okey-doke
=========

Approval Testing for JUnit - like http://approvaltests.sourceforge.net/ but more Java'y
and more the SimplestThingThatCouldPossiblyWork. A [helping hand](http://youtu.be/EbqaxWjIgOg) for many testing problems.


There are three modes of operation, exemplified by

1. [ApprovalsRuleTest](https://github.com/dmcg/okey-doke/blob/master/src/test/java/org/rococoa/okeydoke/ApprovalsRuleTest.java)
 - compare current thing with an approved version and fail with a diff if they aren't the same.
2. [QuickCheckTest](https://github.com/dmcg/okey-doke/blob/master/src/test/java/org/rococoa/okeydoke/QuickCheckTest.java)
 - for easy testing of legacy code.
2. [CommentaryTest](https://github.com/dmcg/okey-doke/blob/master/src/test/java/org/rococoa/okeydoke/CommentaryTest.java)
 - for producing approved files with interactions.

I'll get round to publishing to Maven Central eventually, but in the meantime there are relatively up to date jar files
[here](http://oneeyedmen.com/okeydoke).

