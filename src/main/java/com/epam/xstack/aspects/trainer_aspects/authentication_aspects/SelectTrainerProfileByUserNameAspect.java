package com.epam.xstack.aspects.trainer_aspects.authentication_aspects;

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
public class SelectTrainerProfileByUserNameAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.authentication_aspects.annotations.SelectTrainerProfileByUserNameAspectAnnotation)")
    public void selectTrainerProfileByUserNamePointCut() {
    }

    @Before("selectTrainerProfileByUserNamePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before select Trainer profile by user name method invoked: " + joinPoint.getSignature());
    }

    @After("selectTrainerProfileByUserNamePointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After select Trainer profile by user name method invoked: " + joinPoint.getSignature());
    }
}
