package com.oneeyedmen.okeydoke.pickle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {
    String value();
    String inOrder() default "";
    String as() default "";
    String iWant() default "";
}
