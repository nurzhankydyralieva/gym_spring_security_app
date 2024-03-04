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
public class SaveTraineeAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.SaveTraineeAspectAnnotation)")
    public void saveTraineePointCut() {
    }

    @Before("saveTraineePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before save Trainee method invoked: " + joinPoint.getSignature());
    }

    @After("saveTraineePointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After save Trainee method invoked: " + joinPoint.getSignature());
    }
}
