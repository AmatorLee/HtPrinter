package com.amator.htprinter.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by AmatorLee on 2018/4/4.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
