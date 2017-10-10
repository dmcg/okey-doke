package com.oneeyedmen.okeydoke.junit;

import com.oneeyedmen.okeydoke.Name;
import org.junit.Rule;
import org.junit.Test;

@Name("Test Name Annotation Test")
public class TestNameAnnotationTest {

    @Rule public final ApprovalsRule approver = ApprovalsRule.usualRule();

    @Test
    @Name("my test")
    public void test(){
        approver.assertApproved("banana");
    }
}