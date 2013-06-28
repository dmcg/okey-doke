hamcrest-approvals
==================

Approval Testing for JUnit - like http://approvaltests.sourceforge.net/ but more Java'y
and more the SimpleThingThatCouldPossiblyWork.

There are two modes of operation, exemplified by

1. ApprovalsRuleTest - compare current thing with an approved version and fail with a diff if they aren't the same.
2. TheoryApprovalsRule - do the same thing for all combinations of JUnit @Data and @Theory

