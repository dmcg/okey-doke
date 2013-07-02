hamcrest-approvals
==================

Approval Testing for JUnit - like http://approvaltests.sourceforge.net/ but more Java'y
and more the SimplestThingThatCouldPossiblyWork.

Now with 100% less hamcrest ("Always dispose of the difficult bit in the title").

There are two modes of operation, exemplified by

1. [ApprovalsRuleTest](https://github.com/dmcg/hamcrest-approvals/blob/master/src/test/java/org/hamcrest/approvals/ApprovalsRuleTest.java) - compare current thing with an approved version and fail with a diff if they aren't the same.
2. [QuickCheckTest](https://github.com/dmcg/hamcrest-approvals/blob/master/src/test/java/org/hamcrest/approvals/QuickCheckTest.java) - for easy testing of legacy code.

