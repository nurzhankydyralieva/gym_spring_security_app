package com.epam.xstack.aspects.trainee_aspects.end_points_aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TraineeEndPointAspect {
    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.SaveTraineeEndPointAspectAnnotation)")
    public void saveTraineePointCut() {
    }

    @Before("saveTraineePointCut()")
    public void beforeAdvice() {
        log.info("Save Trainee end point called");
    }

    @After("saveTraineePointCut()")
    public void afterAdvice() {
        log.info("Save Trainee end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.SelectTraineeProfileAspectAnnotation)")
    public void selectTraineePointCut() {
    }

    @Before("selectTraineePointCut()")
    public void beforeSelectAdvice() {
        log.info("Select Trainee profile end point called");
    }

    @After("selectTraineePointCut()")
    public void afterSelectAdvice() {
        log.info("Select Trainee profile end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.UpdateTraineeEndPointAspectAnnotation)")
    public void updateTraineePointCut() {
    }

    @Before("updateTraineePointCut()")
    public void beforeUpdateAdvice() {
        log.info("Update Trainee profile end point called");
    }

    @After("updateTraineePointCut()")
    public void afterUpdateAdvice() {
        log.info("Update Trainee profile end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.UpdateTTListEndPointAspectAnnotation)")
    public void updateTraineeTTLPointCut() {
    }

    @Before("updateTraineeTTLPointCut()")
    public void beforeUpdateTTLAdvice() {
        log.info("Update user profile end point called");
    }

    @After("updateTraineeTTLPointCut()")
    public void afterUpdateTTLAdvice() {
        log.info("Update user profile end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.ActiveDeActiveTraineeEndPointAspectAnnotation)")
    public void activeDeActivePointCut() {
    }

    @Before("activeDeActivePointCut()")
    public void beforeActiveDeActiveAdvice() {
        log.info("updateActivateDe_ActivateTrainee: end point called");
    }

    @After("activeDeActivePointCut()")
    public void afterActiveDeActiveAdvice() {
        log.info("updateActivateDe_ActivateTrainee: end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.DeleteEndPointAspectAnnotation)")
    public void deletePointCut() {
    }

    @Before("deletePointCut()")
    public void beforeDeleteAdvice() {
        log.info("deleteTraineeByUserName: end point called");
    }

    @After("deletePointCut()")
    public void afterDeleteAdvice() {
        log.info("deleteTraineeByUserName: end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.SelectTraineeTLEndPointAspectAnnotation)")
    public void selectTTLPointCut() {
    }

    @Before("selectTTLPointCut()")
    public void beforeSelectTTLAdvice() {
        log.info("selectTraineeTrainingsList: end point called");
    }

    @After("selectTTLPointCut()")
    public void afterSelectTTLAdvice() {
        log.info("selectTraineeTrainingsList: end point ended");
    }

    @Pointcut("@annotation(com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.NotAssignedTraineeEndPointAspectAnnotation)")
    public void notAssignedPointCut() {
    }

    @Before("notAssignedPointCut()")
    public void beforeNotAssignedAdvice() {
        log.info("selectNotAssignedOnTraineeActiveTrainers: end point called");
    }

    @After("notAssignedPointCut()")
    public void afterNotAssignedAdvice() {
        log.info("selectNotAssignedOnTraineeActiveTrainers: end point ended");
    }
}
