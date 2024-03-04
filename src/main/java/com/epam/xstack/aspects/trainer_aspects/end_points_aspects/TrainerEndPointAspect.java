package com.epam.xstack.aspects.trainer_aspects.end_points_aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TrainerEndPointAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.SaveTrainerEndPointAspectAnnotation)")
    public void saveTrainerPointCut() {
    }

    @Before("saveTrainerPointCut()")
    public void beforeAdvice() {
        log.info("Save Trainer end point called");
    }

    @After("saveTrainerPointCut()")
    public void afterAdvice() {
        log.info("Save Trainer end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.SelectTrainerProfileAspectAnnotation)")
    public void selectTrainerPointCut() {
    }

    @Before("selectTrainerPointCut()")
    public void beforeSelectAdvice() {
        log.info("Select Trainer profile end point called");
    }

    @After("selectTrainerPointCut()")
    public void afterSelectAdvice() {
        log.info("Select Trainer profile end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.UpdateTrainerEndPointAspectAnnotation)")
    public void updateTrainerPointCut() {
    }

    @Before("updateTrainerPointCut()")
    public void beforeUpdateAdvice() {
        log.info("Update Trainer profile end point called");
    }

    @After("updateTrainerPointCut()")
    public void afterUpdateAdvice() {
        log.info("Update Trainer profile end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.ActiveDeActiveTrainerEndPointAspectAnnotation)")
    public void activeDeActivePointCut() {
    }

    @Before("activeDeActivePointCut()")
    public void beforeActiveDeActiveAdvice() {
        log.info("updateActivateDe_ActivateTrainer: end point called");
    }

    @After("activeDeActivePointCut()")
    public void afterActiveDeActiveAdvice() {
        log.info("updateActivateDe_ActivateTrainer: end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.SelectTrainerTLEndPointAspectAnnotation)")
    public void selectTTLPointCut() {
    }

    @Before("selectTTLPointCut()")
    public void beforeSelectTTLAdvice() {
        log.info("selectTrainerTrainingsList: end point called");
    }

    @After("selectTTLPointCut()")
    public void afterSelectTTLAdvice() {
        log.info("selectTrainerTrainingsList: end point ended");
    }
}
