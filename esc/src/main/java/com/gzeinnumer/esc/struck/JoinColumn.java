package com.gzeinnumer.esc.struck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {
    String withTable();
    String columnName();
    String alias() default "";
}
