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
public class UpdateTrainerProfileAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.authentication_aspects.annotations.UpdateTrainerProfileAspectAnnotation)")
    public void updateTrainerProfilePointCut() {
    }

    @Before("updateTrainerProfilePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before update Trainer profile method invoked: " + joinPoint.getSignature());
    }

    @After("updateTrainerProfilePointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After update Trainer profile method invoked: " + joinPoint.getSignature());
    }
}
