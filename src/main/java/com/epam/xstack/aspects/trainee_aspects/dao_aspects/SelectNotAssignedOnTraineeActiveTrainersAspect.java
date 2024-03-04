package com.epam.xstack.aspects.trainee_aspects.dao_aspects;

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

public class SelectNotAssignedOnTraineeActiveTrainersAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.SelectNotAssignedOnTraineeActiveTrainersAspectAnnotation)")
    public void notAssignedPointCut() {
    }

    @Before("notAssignedPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before select not assigned on Trainee active Trainer method invoked: " + joinPoint.getSignature());
    }

    @After("notAssignedPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After select not assigned on Trainee active Trainer method invoked: " + joinPoint.getSignature());
    }
}
