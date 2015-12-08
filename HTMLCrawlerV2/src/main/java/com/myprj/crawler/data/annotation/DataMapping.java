package com.myprj.crawler.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DienNM (DEE)
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataMapping {
    
    /**
     * Declare value of field name.
     * @return String
     */
    String value();
}
