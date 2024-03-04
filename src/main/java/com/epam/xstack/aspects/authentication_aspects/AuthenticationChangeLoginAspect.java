package com.epam.xstack.aspects.authentication_aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuthenticationChangeLoginAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.authentication_aspects.annotations.AuthenticationChangeLoginAspectAnnotation)")
    public void authenticationChangeLoginPointCut() {
    }

    @Before("authenticationChangeLoginPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before authentication change login method invoked: " + joinPoint.getSignature());
    }

    @After("authenticationChangeLoginPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After authentication change login method invoked: " + joinPoint.getSignature());
    }
}
