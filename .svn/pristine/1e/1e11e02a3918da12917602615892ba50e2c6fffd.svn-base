package com.wasion.meterreading.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IpAccessLimit {
    /**
     * 时间类型，默认毫秒
     * 
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 多长时间内限制，默认 60
     * 
     * @return
     */
    long t() default 60000;

    /**
     * 单位时间内能访问多少次，默认10次
     * 
     * @return
     */
    int count() default 10;
}
