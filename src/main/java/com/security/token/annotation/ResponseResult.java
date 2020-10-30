package com.security.token.annotation;

import java.lang.annotation.*;

/**
 * 响应结果注解
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/31
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {
    /**
     * 是否必须
     *
     * @return {true(default)|false}
     */
    boolean required() default true;
}
