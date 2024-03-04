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
public class UpdateTraineesTrainerListAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.ActivateDe_ActivateTraineeAspectAnnotation)")
    public void updateTraineesTrainerListPointCut() {
    }

    @Before("updateTraineesTrainerListPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before update Trainees Trainer List method invoked: " + joinPoint.getSignature());
    }

    @After("updateTraineesTrainerListPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After update Trainees Trainer List method invoked: " + joinPoint.getSignature());
    }
}
