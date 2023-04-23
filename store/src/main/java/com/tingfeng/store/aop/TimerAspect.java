package com.tingfeng.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimerAspect {

    @Around("execution(* com.tingfeng.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Long start = System.currentTimeMillis();
        Object result = pjp.proceed(); //调用目标方法
        Long end = System.currentTimeMillis();
        System.err.println("耗时："+(end-start));
        return result;
    }
}
