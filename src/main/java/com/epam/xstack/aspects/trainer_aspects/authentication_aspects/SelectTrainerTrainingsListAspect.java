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
public class SelectTrainerTrainingsListAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.authentication_aspects.annotations.SelectTrainerTrainingsListAspectAnnotation)")
    public void selectTrainerTrainingsListPointCut() {
    }

    @Before("selectTrainerTrainingsListPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before select Trainer Trainings List method invoked: " + joinPoint.getSignature());
    }

    @After("selectTrainerTrainingsListPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After select Trainer Trainings List method invoked: " + joinPoint.getSignature());
    }
}
