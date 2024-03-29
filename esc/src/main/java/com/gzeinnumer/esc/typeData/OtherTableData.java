package com.gzeinnumer.esc.typeData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OtherTableData {
    String withTable() ;
//    String columnName();
    String alias();
}