package com.param.smartsecurity.config.dynamicdatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @Author Mc QiLing
 * @Date 2020/11/9 4:10 下午
 * @Version 1.0
 */

@Component
@Order(value = -100)
@Slf4j
@Aspect
public class DataSourceAspect {

    @Before("execution(* com.param.smartsecurity.service.impl.*.*(..)) && @annotation(com.param.smartsecurity.config.dynamicdatasource.MyDataSource)")
    public void before(JoinPoint joinPoint) {
    	// execution 中配置的是服务实现类 & MyDataSource的包路径
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyDataSource myDataSource = null;
        // 判断方法上的注解
        if (method.isAnnotationPresent(MyDataSource.class)) {
            myDataSource = method.getAnnotation(MyDataSource.class);
            DataSourceContextHolder.setDbType(myDataSource.value());
        } else if (method.getDeclaringClass().isAnnotationPresent(MyDataSource.class)) {
            //其次判断类上的注解
            myDataSource = method.getDeclaringClass().getAnnotation(MyDataSource.class);
            DataSourceContextHolder.setDbType(myDataSource.value());
        }
        if (myDataSource != null) {
            log.info("注解方式选择数据源---" + myDataSource.value().getValue());
        }
    }

    /**
     * 服务类的方法结束后，会清除数据源，此时会变更为默认的数据源
     **/
    @After("execution(* com.param.smartsecurity.service.impl.*.*(..)) && @annotation(com.param.smartsecurity.config.dynamicdatasource.MyDataSource)")
    public void after(JoinPoint point){
    	// execution 中配置的是服务实现类 & MyDataSource的包路径
        DataSourceContextHolder.clearDbType();
    }
}