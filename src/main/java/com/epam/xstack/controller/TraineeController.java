package com.epam.xstack.controller;

import com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.*;
import com.epam.xstack.exceptions.validator.NotNullValidation;
import com.epam.xstack.models.dto.trainee_dto.request.*;
import com.epam.xstack.models.dto.trainee_dto.response.*;
import com.epam.xstack.models.entity.Trainee;
import com.epam.xstack.service.trainee_service.TraineeService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/trainees")
@PreAuthorize("hasRole('TRAINEE')")
@Tag(name = "Trainee controller")
public class TraineeController {
    private TraineeService traineeService;
    private NotNullValidation validation;
    private List<Trainee> traineeList = new ArrayList<>();
    private MeterRegistry registry;

    public Supplier<Number> fetchUserCount() {
        return () -> traineeList.stream().count();
    }

    @Autowired
    public TraineeController(TraineeService traineeService, NotNullValidation validation, MeterRegistry registry) {
        this.traineeService = traineeService;
        this.validation = validation;
        this.registry = registry;
        Gauge.builder("trainee.controller.trainee.count", fetchUserCount())
                .tag("version", "gym.app")
                .description("Trainee Controller description")
                .register(registry);
    }

    @Operation(summary = "Get Trainee by user name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User selected successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @SelectTraineeProfileAspectAnnotation
    @PreAuthorize("hasAuthority('trainee:read')")
    @GetMapping("/{id}")
    public ResponseEntity<TraineeProfileSelectResponseDTO> selectTraineeProfile(@PathVariable("id") UUID id, @Valid @RequestBody TraineeProfileSelectRequestDTO requestDTO, BindingResult result) {
        validation.userNotNullValidation(result);
        return new ResponseEntity<>(traineeService.selectTraineeProfileByUserName(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Update Trainee in database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @UpdateTraineeEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainee:update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<TraineeProfileUpdateResponseDTO> updateUser(@PathVariable("id") UUID id, @Valid @RequestBody TraineeProfileUpdateRequestDTO requestDTO, BindingResult result) {
        validation.nullValidation(result);
        return new ResponseEntity<>(traineeService.updateTraineeProfile(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Update Trainee's Trainer List in database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials")})
    @UpdateTTListEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainee:update')")
    @PutMapping("/update-list/{id}")
    public ResponseEntity<TraineesTrainerListUpdateResponseDTO> updateTraineesTrainerList(@PathVariable("id") UUID id, @Valid @RequestBody TraineesTrainerListUpdateRequestDTO requestDTO, BindingResult result) {
        validation.nullValidation(result);
        return new ResponseEntity<>(traineeService.updateTraineesTrainerList(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Update active or de active Trainee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User activated successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @ActiveDeActiveTraineeEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainee:update')")
    @PatchMapping("/{id}")
    public ResponseEntity<TraineeOkResponseDTO> updateActivateDe_ActivateTrainee(@PathVariable("id") UUID id, @Valid @RequestBody TraineeActivateDeActivateDTO dto, BindingResult result) {
        validation.userNotNullValidation(result);
        return new ResponseEntity<>(traineeService.activateDe_ActivateTrainee(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Delete Trainee by user name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @DeleteEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainee:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TraineeOkResponseDTO> deleteTraineeByUserName(@PathVariable("id") UUID id, @Valid @RequestBody TraineeProfileSelectRequestDTO requestDTO, BindingResult result) {
        validation.userNotNullValidation(result);
        return new ResponseEntity<>(traineeService.deleteTraineeByUserName(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get Trainee Trainings List",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User selected successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @SelectTraineeTLEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainee:read')")
    @GetMapping("/select/{id}")
    public ResponseEntity<TraineeTrainingsListResponseDTO> selectTraineeTrainingsList(@PathVariable("id") UUID id, @Valid @RequestBody TraineeTrainingsListRequestDTO requestDTO, BindingResult result) {
        validation.userNotNullValidation(result);
        return new ResponseEntity<>(traineeService.selectTraineeTrainingsList(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get not assigned on trainee active trainers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User selected successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "403", description = "Access denied, check user name or id"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @NotAssignedTraineeEndPointAspectAnnotation
    @PreAuthorize("hasAuthority('trainee:read')")
    @GetMapping("/active-not-assigned/{id}")
    public ResponseEntity<TraineesTrainerActiveAndNotAssignedResponseDTO> selectNotAssignedOnTraineeActiveTrainers(@PathVariable("id") UUID id, @Valid @RequestBody TraineesTrainerActiveAndNotAssignedRequestDTO userName, BindingResult result) {
        validation.userNotNullValidation(result);
        return new ResponseEntity<>(traineeService.selectNotAssignedOnTraineeActiveTrainers(id, userName), HttpStatus.OK);
    }

}
