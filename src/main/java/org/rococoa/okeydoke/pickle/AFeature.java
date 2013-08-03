package org.rococoa.okeydoke.pickle;

public @interface AFeature {
    String description();
    String inOrder() default "";
    String asA() default "";
    String iWant() default "";
}
