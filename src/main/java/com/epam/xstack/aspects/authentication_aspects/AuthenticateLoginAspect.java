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
public class AuthenticateLoginAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.authentication_aspects.annotations.AuthenticationLoginAspectAnnotation)")
    public void authenticateLoginPointCut() {
    }

    @Before("authenticateLoginPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before authenticate login method invoked: " + joinPoint.getSignature());
    }

    @After("authenticateLoginPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After authenticate login method invoked: " + joinPoint.getSignature());
    }
}
