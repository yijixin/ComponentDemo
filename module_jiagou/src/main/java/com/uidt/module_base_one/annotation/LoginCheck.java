package com.uidt.module_base_one.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yijixin on 2019-12-13
 */
//作用于方法上
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface LoginCheck {
}
