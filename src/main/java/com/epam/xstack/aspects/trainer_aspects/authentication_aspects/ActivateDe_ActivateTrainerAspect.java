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
public class ActivateDe_ActivateTrainerAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.authentication_aspects.annotations.ActivateDe_ActivateTrainerAspectAnnotation)")
    public void activeDe_ActiveTrainerPointCut() {
    }

    @Before("activeDe_ActiveTrainerPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before activate deactivate Trainer method invoked: " + joinPoint.getSignature());
    }

    @After("activeDe_ActiveTrainerPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After activate deactivate Trainer method invoked: " + joinPoint.getSignature());
    }
}
