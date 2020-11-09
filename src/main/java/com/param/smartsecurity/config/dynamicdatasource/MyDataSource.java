package com.param.smartsecurity.config.dynamicdatasource;

import java.lang.annotation.*;


/**
 * @Author Mc QiLing
 * @Date 2020/11/9 4:10 下午
 * @Version 1.0
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyDataSource {
    CommonEnum value() default CommonEnum.DB1;
}