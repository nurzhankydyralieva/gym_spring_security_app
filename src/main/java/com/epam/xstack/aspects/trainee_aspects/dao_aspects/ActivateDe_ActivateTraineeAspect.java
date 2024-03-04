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
public class ActivateDe_ActivateTraineeAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.ActivateDe_ActivateTraineeAspectAnnotation)")
    public void activeDe_ActiveTraineePointCut() {
    }

    @Before("activeDe_ActiveTraineePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before activate deactivate Trainee method invoked: " + joinPoint.getSignature());
    }

    @After("activeDe_ActiveTraineePointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After activate deactivate Trainee method invoked: " + joinPoint.getSignature());
    }
}
