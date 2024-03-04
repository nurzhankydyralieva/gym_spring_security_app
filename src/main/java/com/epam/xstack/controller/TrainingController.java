package com.epam.xstack.controller;

import com.epam.xstack.aspects.training_aspects.annotations.SaveTrainingEndPointAspectAnnotation;
import com.epam.xstack.exceptions.validator.NotNullValidation;
import com.epam.xstack.models.dto.training_dto.request.TrainingSaveRequestDTO;
import com.epam.xstack.models.dto.training_dto.response.TrainingSaveResponseDTO;
import com.epam.xstack.models.entity.Training;
import com.epam.xstack.service.training_service.TrainingService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/trainings")
@SecurityRequirement(name = "spring-security-gym-app")
@PreAuthorize("hasRole('TRAINER')")
@Tag(name = "Training controller")
public class TrainingController {
    private final TrainingService trainingService;
    private final NotNullValidation validation;
    private List<Training> trainingList = new ArrayList<>();
    private final MeterRegistry registry;

    public Supplier<Number> fetchTrainingCount() {
        return () -> trainingList.stream().count();
    }

    @Autowired
    public TrainingController(TrainingService trainingService, NotNullValidation validation, MeterRegistry registry) {
        this.trainingService = trainingService;
        this.validation = validation;
        this.registry = registry;
        Gauge.builder("training.controller.training.count", fetchTrainingCount())
                .tag("version", "gym.app")
                .description("Training Controller description")
                .register(registry);
    }

    @Operation(summary = "Save Training to database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training saved successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials")})
    @SaveTrainingEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainer:create')")
    @PostMapping("/save")
    public ResponseEntity<TrainingSaveResponseDTO> saveTraining(@Valid @RequestBody TrainingSaveRequestDTO requestDTO, BindingResult result) {
        validation.nullValidation(result);
        return new ResponseEntity<>(trainingService.saveTraining(requestDTO), HttpStatus.CREATED);
    }
}
