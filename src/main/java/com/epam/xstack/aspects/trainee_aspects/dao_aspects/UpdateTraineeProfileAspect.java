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
public class UpdateTraineeProfileAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.UpdateTraineeProfileAspectAnnotation)")
    public void updateTraineeProfilePointCut() {
    }

    @Before("updateTraineeProfilePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before update Trainee profile method invoked: " + joinPoint.getSignature());
    }

    @After("updateTraineeProfilePointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After update Trainee profile method invoked: " + joinPoint.getSignature());
    }
}
