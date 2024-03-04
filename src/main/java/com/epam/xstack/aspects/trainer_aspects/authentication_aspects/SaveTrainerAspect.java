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
public class SaveTrainerAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.authentication_aspects.annotations.SaveTraineeAspectAnnotation)")
    public void saveTrainerPointCut() {
    }

    @Before("saveTrainerPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before save Trainer method invoked: " + joinPoint.getSignature());
    }

    @After("saveTrainerPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After save Trainer method invoked: " + joinPoint.getSignature());
    }
}
