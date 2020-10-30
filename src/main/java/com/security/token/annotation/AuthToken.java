package com.security.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来认证的token注解
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {
    /**
     * 是否必须
     *
     * @return {true(default)|false}
     */
    boolean required() default true;
}
