package com.epam.xstack.aspects.training_aspects;

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
public class TrainingSaveAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.training_aspects.annotations.TrainingSaveAspectAnnotation)")
    public void saveTrainingPointCut() {
    }

    @Before("saveTrainingPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before save training method invoked: " + joinPoint.getSignature());
    }

    @After("saveTrainingPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After save training method invoked: " + joinPoint.getSignature());
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.training_aspects.annotations.SaveTrainingEndPointAspectAnnotation)")
    public void saveTrainingEndPointPointCut() {
    }

    @Before("saveTrainingEndPointPointCut()")
    public void beforeEndPointAdvice() {
        log.info("Before save training end point invoked");
    }

    @After("saveTrainingEndPointPointCut()")
    public void afterEndPointAdvice() {
        log.info("After save training end point invoked");
    }
}
