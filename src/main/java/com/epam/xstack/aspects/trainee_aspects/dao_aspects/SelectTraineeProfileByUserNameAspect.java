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
public class SelectTraineeProfileByUserNameAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.SelectTraineeProfileByUserNameAspectAnnotation)")
    public void selectTraineeProfileByUserNamePointCut() {
    }

    @Before("selectTraineeProfileByUserNamePointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before select Trainee profile by user name method invoked: " + joinPoint.getSignature());
    }

    @After("selectTraineeProfileByUserNamePointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After select Trainee profile by user name method invoked: " + joinPoint.getSignature());
    }
}
