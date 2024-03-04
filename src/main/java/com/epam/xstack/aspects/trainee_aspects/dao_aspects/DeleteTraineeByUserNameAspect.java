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
public class DeleteTraineeByUserNameAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.DeleteTraineeByUserNameAspectAnnotation)")
    public void deleteTraineeByUserNamePointCut() {
    }

    @Before("deleteTraineeByUserNamePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before delete Trainee by user name method invoked: " + joinPoint.getSignature());
    }

    @After("deleteTraineeByUserNamePointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After delete Trainee by user name method invoked: " + joinPoint.getSignature());
    }
}
