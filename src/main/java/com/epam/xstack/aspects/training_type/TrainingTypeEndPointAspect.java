package com.epam.xstack.aspects.training_type;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TrainingTypeEndPointAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.training_type.annotations.SaveTrainingTypeEndPointAspectAnnotation)")
    public void saveTrainingTypeEndPointPointCut() {
    }

    @Before("saveTrainingTypeEndPointPointCut()")
    public void beforeEndPointAdvice() {
        log.info("Before save Training type end point invoked:");
    }

    @After("saveTrainingTypeEndPointPointCut()")
    public void afterEndPointAdvice() {
        log.info("After save Training type end point invoked:");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.training_type.annotations.SelectAllTrainingTypeEndPointAspectAnnotation)")
    public void saveAllEndPointPointCut() {
    }

    @Before("saveAllEndPointPointCut()")
    public void beforeAllEndPointAdvice() {
        log.info("Before select all Training type end point invoked:");
    }

    @After("saveAllEndPointPointCut()")
    public void afterAllEndPointAdvice() {
        log.info("After select all Training type end point invoked:");
    }
}
