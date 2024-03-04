package com.epam.xstack.controller;

import com.epam.xstack.aspects.authentication_aspects.annotations.AuthenticationChangeLoginAspectAnnotation;
import com.epam.xstack.aspects.authentication_aspects.annotations.AuthenticationLoginAspectAnnotation;
import com.epam.xstack.aspects.trainee_aspects.end_points_aspects.annotations.SaveTraineeEndPointAspectAnnotation;
import com.epam.xstack.aspects.trainer_aspects.end_points_aspects.annotations.SaveTrainerEndPointAspectAnnotation;
import com.epam.xstack.exceptions.validator.NotNullValidation;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationChangeLoginRequestDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationChangeLoginResponseDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationRequestDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationResponseDTO;
import com.epam.xstack.models.dto.trainee_dto.request.TraineeRegistrationRequestDTO;
import com.epam.xstack.models.dto.trainee_dto.response.TraineeRegistrationResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerRegistrationRequestDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerRegistrationResponseDTO;
import com.epam.xstack.models.entity.User;
import com.epam.xstack.service.authentication_service.impl.AuthenticationServiceImpl;
import com.epam.xstack.service.trainee_service.TraineeService;
import com.epam.xstack.service.trainer_service.TrainerService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "spring-security-gym-app")
@Tag(name = "Authentication controller")
public class AuthenticationController {
    private AuthenticationServiceImpl service;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private NotNullValidation validation;
    private List<User> userList = new ArrayList<>();
    private MeterRegistry registry;

    public Supplier<Number> fetchUserCount() {
        return () -> userList.stream().count();
    }

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl service, TrainerService trainerService,
                                    TraineeService traineeService, NotNullValidation validation, MeterRegistry registry) {
        this.service = service;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.validation = validation;
        this.registry = registry;
        Gauge.builder("authentication.controller.user.count", fetchUserCount())
                .tag("version", "gym.app")
                .description("Authentication Controller description")
                .register(registry);
    }

    @Operation(summary = "Save Trainer to database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User saved successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null")})
    @SaveTrainerEndPointAspectAnnotation
    @PostMapping("/register-trainer")
    public ResponseEntity<TrainerRegistrationResponseDTO> registerTrainer(@Valid @RequestBody TrainerRegistrationRequestDTO request, BindingResult result) {
        validation.nullValidation(result);
        return ResponseEntity.ok(trainerService.registerTrainer(request));
    }

    @Operation(summary = "Save Trainee to database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User saved successfully"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null")})
    @SaveTraineeEndPointAspectAnnotation
    @PostMapping("/register-trainee")
    public ResponseEntity<TraineeRegistrationResponseDTO> registerTrainee(@Valid @RequestBody TraineeRegistrationRequestDTO request, BindingResult result) {
        validation.nullValidation(result);
        return ResponseEntity.ok(traineeService.registerTrainee(request));
    }

    @Operation(summary = "This request changes login and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "403", description = "Access denied, check user name or id"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @AuthenticationLoginAspectAnnotation
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@Valid @RequestBody AuthenticationRequestDTO request, BindingResult result) {
        validation.nullValidation(result);
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(summary = "This request changes login and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User password and login successfully updated"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "403", description = "Access denied, check user name or id"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @AuthenticationChangeLoginAspectAnnotation
    @PatchMapping("/update/{id}")
    public ResponseEntity<AuthenticationChangeLoginResponseDTO> updateLogin(@PathVariable("id") UUID id, @Valid @RequestBody AuthenticationChangeLoginRequestDTO requestDTO, BindingResult bindingResult) {
        validation.nullValidation(bindingResult);
        return new ResponseEntity<>(service.authenticationChangeLogin(id, requestDTO), HttpStatus.OK);
    }

    @Operation(summary = "This request refreshes the token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User password and login successfully updated"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials"),
                    @ApiResponse(responseCode = "422", description = "User or password is null"),
                    @ApiResponse(responseCode = "403", description = "Access denied, check user name or id"),
                    @ApiResponse(responseCode = "404", description = "User with user name or id not found")})
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
