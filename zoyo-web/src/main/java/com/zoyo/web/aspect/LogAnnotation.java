package com.zoyo.web.aspect;

import java.lang.annotation.*;

/**
 * @Author: mxx
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 操作模块
     */
    String module() default "";

    /**
     * 操作说明
     */
    String operaDesc() default "";

}
