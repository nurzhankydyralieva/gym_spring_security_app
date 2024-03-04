package com.epam.xstack.controller;

import com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.ActiveDeActiveTrainerEndPointAspectAnnotation;
import com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.SelectTrainerProfileAspectAnnotation;
import com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.SelectTrainerTLEndPointAspectAnnotation;
import com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.UpdateTrainerEndPointAspectAnnotation;
import com.epam.xstack.exceptions.validator.NotNullValidation;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerActivateDeActivateDTO;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerProfileSelectRequestDTO;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerProfileUpdateRequestDTO;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerTrainingsListRequestDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerOkResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerProfileSelectResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerProfileUpdateResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerTrainingsListResponseDTO;
import com.epam.xstack.models.entity.Trainer;
import com.epam.xstack.service.trainer_service.TrainerService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;


@RestController
@RequestMapping("/api/v1/trainers")
@PreAuthorize("hasRole('TRAINER')")
@Tag(name = "Trainer controller")
public class TrainerController {
    private  TrainerService trainerService;
    private  NotNullValidation validation;
    private List<Trainer> trainerList = new ArrayList<>();
    private  MeterRegistry registry;

    public Supplier<Number> fetchUserCount() {
        return () -> trainerList.stream().count();
    }

    @Autowired
    public TrainerController(TrainerService trainerService, NotNullValidation validation, MeterRegistry registry) {
        this.trainerService = trainerService;
        this.validation = validation;
        this.registry = registry;
        Gauge.builder("trainer.controller.trainer.count", fetchUserCount())
                .tag("version", "gym.app")
                .description("Trainer Controller description")
                .register(registry);
    }

    @Operation(summary = "Get Trainer by user name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User selected successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @SelectTrainerProfileAspectAnnotation
    @PreAuthorize("hasAuthority('trainer:read')")
    @GetMapping("/{id}")
    public ResponseEntity<TrainerProfileSelectResponseDTO> selectTrainerProfile(@PathVariable("id") UUID id, @Valid @RequestBody TrainerProfileSelectRequestDTO requestDTO, BindingResult result) {
        validation.userNotNullValidation(result);
        return new ResponseEntity<>(trainerService.selectTrainerProfileByUserName(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Update Trainer in database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @UpdateTrainerEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainer:update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<TrainerProfileUpdateResponseDTO> updateUser(@PathVariable("id") UUID id, @Valid @RequestBody TrainerProfileUpdateRequestDTO requestDTO, BindingResult result) {
        validation.nullValidation(result);
        return new ResponseEntity<>(trainerService.updateTrainerProfile(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Update active or de active Trainer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User activated successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied, check user name or id"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @ActiveDeActiveTrainerEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainer:update')")
    @PatchMapping("/{id}")
    public ResponseEntity<TrainerOkResponseDTO> updateActivateDe_ActivateTrainer(@PathVariable("id") UUID id, @Valid @RequestBody TrainerActivateDeActivateDTO dto, BindingResult result) {
        validation.nullValidation(result);
        return new ResponseEntity<>(trainerService.activateDe_ActivateTrainer(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Get Trainer Trainings List",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User selected successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied, check user name or id"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @SelectTrainerTLEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainer:read')")
    @GetMapping("/select/{id}")
    public ResponseEntity<TrainerTrainingsListResponseDTO> selectTrainerTrainingsList(@PathVariable("id") UUID id, @Valid @RequestBody TrainerTrainingsListRequestDTO requestDTO, BindingResult result) {
        validation.userNotNullValidation(result);
        return new ResponseEntity<>(trainerService.selectTrainerTrainingsList(id, requestDTO), HttpStatus.OK);
    }

}
