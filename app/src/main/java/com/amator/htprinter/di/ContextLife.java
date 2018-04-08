package com.amator.htprinter.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by AmatorLee on 2018/4/5.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {

    String value() default "";

}
