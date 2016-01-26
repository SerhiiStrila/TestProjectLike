package com.serhii.strila.testproject.di;

/**
 * Created by Serhii Strila on 03.07.15
 */

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope
@Retention(RUNTIME)
public @interface PerApp {

}
