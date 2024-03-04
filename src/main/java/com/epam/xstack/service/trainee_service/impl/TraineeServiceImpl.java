package com.epam.xstack.service.trainee_service.impl;

import com.epam.xstack.actuators.prometheuses.UserSessionMetrics;
import com.epam.xstack.aspects.trainee_aspects.dao_aspects.annotations.*;
import com.epam.xstack.configuration.JwtService;
import com.epam.xstack.exceptions.exception.UserIdNotFoundException;
import com.epam.xstack.exceptions.exception.UserNameNotExistsException;
import com.epam.xstack.exceptions.generator.PasswordUserNameGenerator;
import com.epam.xstack.exceptions.validator.ActivationValidator;
import com.epam.xstack.exceptions.validator.SaveTokenValidation;
import com.epam.xstack.exceptions.validator.UserNameExistenceValidator;
import com.epam.xstack.mapper.trainee_mapper.*;
import com.epam.xstack.mapper.trainer_mapper.TrainerMapper;
import com.epam.xstack.mapper.training_mapper.TraineeTrainingMapper;
import com.epam.xstack.models.dto.trainee_dto.request.*;
import com.epam.xstack.models.dto.trainee_dto.response.*;
import com.epam.xstack.models.entity.Trainee;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.models.enums.Role;
import com.epam.xstack.repository.TraineeRepository;
import com.epam.xstack.repository.UserRepository;
import com.epam.xstack.service.trainee_service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Profile(value = {"local","dev","prod","stg"})
public class TraineeServiceImpl implements TraineeService {
    private final UserRepository repository;
    private final TraineeRepository traineeRepository;
    private final SaveTokenValidation tokenValidation;
    private final ActivationValidator checkActivation;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordUserNameGenerator generator;
    private final UserNameExistenceValidator checkUserNameExistence;
    private final TraineeProfileSelectRequestMapper getTraineeProfileRequestMapper;
    private final TraineeProfileUpdateRequestMapper updateTraineeProfileRequestMapper;
    private final TraineeActivateDeActivateMapper activateDeActivateTraineeMapper;
    private final TraineeTrainingsListMapper traineeTrainingsListMapper;
    private final TraineesTrainerListUpdateMapper traineesTrainerListUpdateMapper;
    private final UserSessionMetrics userSessionMetrics;
    private AtomicInteger activeSessions = new AtomicInteger(0);


    @Override
    @SaveTraineeAspectAnnotation
    public TraineeRegistrationResponseDTO registerTrainee(TraineeRegistrationRequestDTO request) {
        String generatedPassword = generator.generateRandomPassword();
        String createdUserName = generator.generateUserName(request.getFirstName(), request.getLastName());

        var createTrainee = new Trainee();
        createTrainee.setUserName(createdUserName);
        createTrainee.setFirstName(request.getFirstName());
        createTrainee.setLastName(request.getLastName());
        createTrainee.setAddress(request.getAddress());
        createTrainee.setDateOfBirth(request.getDateOfBirth());
        createTrainee.setPassword(passwordEncoder.encode(generatedPassword));
        createTrainee.setRole(Role.TRAINEE);
        createTrainee.setIsActive(true);
        createTrainee.setIsAssigned(false);

        checkUserNameExistence.userNameExists(createdUserName);
        var savedUser = repository.save(createTrainee);
        activeSessions.incrementAndGet();
        userSessionMetrics.incrementActiveSessions();
        var jwtToken = jwtService.generateToken(createTrainee);
        tokenValidation.saveUserToken(savedUser, jwtToken);
        return TraineeRegistrationResponseDTO.builder()
                .password(generatedPassword)
                .userName(createTrainee.getUsername())
                .build();
    }

    @Override
    @SelectTraineeProfileByUserNameAspectAnnotation
    public TraineeProfileSelectResponseDTO selectTraineeProfileByUserName(UUID id, TraineeProfileSelectRequestDTO requestDTO) {

        Trainee trainee = getTraineeProfileRequestMapper.toEntity(requestDTO);
        Trainee traineeId = traineeRepository.findById(id).get();


        if (traineeId.getUsername().equals(requestDTO.getUserName())) {
            getTraineeProfileRequestMapper.toDto(trainee);

            return TraineeProfileSelectResponseDTO
                    .builder()
                    .firstName(traineeId.getFirstName())
                    .lastName(traineeId.getLastName())
                    .address(traineeId.getAddress())
                    .isActive(traineeId.getIsActive())
                    .dateOfBirth(traineeId.getDateOfBirth())
                    .trainers(TrainerMapper.INSTANCE.toDtos(traineeId.getTrainers()))
                    .build();
        } else {
            throw UserNameNotExistsException.builder()
                    .codeStatus(Code.USER_NOT_FOUND)
                    .message("User with name - " + requestDTO.getUserName() + " not exists in database")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    @UpdateTraineeProfileAspectAnnotation
    public TraineeProfileUpdateResponseDTO updateTraineeProfile(UUID id, TraineeProfileUpdateRequestDTO requestDTO) {
        Trainee trainee = updateTraineeProfileRequestMapper.toEntity(requestDTO);
        Trainee traineeToBeUpdated = traineeRepository.findById(id).get();

        if (traineeToBeUpdated.getId() == id) {
            traineeToBeUpdated.setFirstName(requestDTO.getFirstName());
            traineeToBeUpdated.setLastName(requestDTO.getLastName());
            traineeToBeUpdated.setDateOfBirth(requestDTO.getDateOfBirth());
            traineeToBeUpdated.setAddress(requestDTO.getAddress());
            traineeToBeUpdated.setIsActive(requestDTO.getIsActive());

            traineeRepository.save(traineeToBeUpdated);
            updateTraineeProfileRequestMapper.toDto(trainee);
        }

        return TraineeProfileUpdateResponseDTO
                .builder()
                .userName(traineeToBeUpdated.getUsername())
                .firstName(traineeToBeUpdated.getFirstName())
                .lastName(traineeToBeUpdated.getLastName())
                .dateOfBirth(traineeToBeUpdated.getDateOfBirth())
                .address(traineeToBeUpdated.getAddress())
                .isActive(traineeToBeUpdated.getIsActive())
                .trainers(TrainerMapper.INSTANCE.toDtos(traineeToBeUpdated.getTrainers()))
                .build();
    }

    @Override
    @ActivateDe_ActivateTraineeAspectAnnotation
    public TraineeOkResponseDTO activateDe_ActivateTrainee(UUID id, TraineeActivateDeActivateDTO dto) {
        Trainee trainee = activateDeActivateTraineeMapper.toEntity(dto);
        Trainee existingTrainee = traineeRepository.findById(id).get();

        checkActivation.checkActiveOrNotTraineeActive(id, dto);

        existingTrainee.setIsActive(dto.getIsActive());
        traineeRepository.save(existingTrainee);
        activateDeActivateTraineeMapper.toDto(trainee);
        return TraineeOkResponseDTO
                .builder()
                .code(Code.STATUS_200_OK)
                .response("Activate DeActivate Trainee updated")
                .build();

    }

    @Override
    @DeleteTraineeByUserNameAspectAnnotation
    public TraineeOkResponseDTO deleteTraineeByUserName(UUID id, TraineeProfileSelectRequestDTO requestDTO) {
        Trainee traineeId = traineeRepository.findById(id).get();

        if (traineeId.getUsername().equals(requestDTO.getUserName())) {
            traineeRepository.deleteById(traineeId.getId());
            activeSessions.decrementAndGet();
            userSessionMetrics.decrementActiveSessions();
            return TraineeOkResponseDTO
                    .builder()
                    .response("Trainee is deleted from database")
                    .code(Code.STATUS_200_OK)
                    .build();
        } else {
            throw UserNameNotExistsException
                    .builder()
                    .message("Trainee is not available in database")
                    .codeStatus(Code.USER_NOT_FOUND)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    @SelectTraineeTrainingsListAspectAnnotation
    public TraineeTrainingsListResponseDTO selectTraineeTrainingsList(UUID id, TraineeTrainingsListRequestDTO requestDTO) {
        Trainee traineeId = traineeRepository.findById(id).get();
        Trainee trainee = traineeTrainingsListMapper.toEntity(requestDTO);

        if (traineeId.getUsername().equals(requestDTO.getUserName())) {
            traineeTrainingsListMapper.toDto(trainee);
            return TraineeTrainingsListResponseDTO
                    .builder()
                    .trainings(TraineeTrainingMapper.INSTANCE.toDtos(traineeId.getTrainings()))
                    .build();
        } else {
            throw UserIdNotFoundException.builder()
                    .codeStatus(Code.USER_ID_NOT_FOUND)
                    .message("User id:  " + traineeId.getId() + " or user name: " + requestDTO.getUserName() + " not correct.")
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }
    }

    @Override
    @UpdateTraineesTrainerListAspectAnnotation
    public TraineesTrainerListUpdateResponseDTO updateTraineesTrainerList(UUID id, TraineesTrainerListUpdateRequestDTO requestDTO) {
        Trainee traineeToBeUpdated = traineeRepository.findById(id).get();
        Trainee trainee = traineesTrainerListUpdateMapper.toEntity(requestDTO);

        if (traineeToBeUpdated.getId() == null || !traineeToBeUpdated.getUsername().equals(requestDTO.getUserName())) {
            throw UserNameNotExistsException
                    .builder()
                    .codeStatus(Code.STATUS_VALIDATION_ERROR)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("User or id not exists.")
                    .build();
        } else {
            traineeToBeUpdated.getTrainers().forEach(trainer -> {
                trainer.setUserName(traineeToBeUpdated.getUsername());
            });
            traineeRepository.save(traineeToBeUpdated);
            traineesTrainerListUpdateMapper.toDto(trainee);
            return TraineesTrainerListUpdateResponseDTO
                    .builder()
                    .trainers(TrainerMapper.INSTANCE.toDtos(traineeToBeUpdated.getTrainers()))
                    .build();
        }
    }

    @Override
    @SelectNotAssignedOnTraineeActiveTrainersAspectAnnotation
    public TraineesTrainerActiveAndNotAssignedResponseDTO selectNotAssignedOnTraineeActiveTrainers(UUID id, TraineesTrainerActiveAndNotAssignedRequestDTO requestDTO) {
        Trainee traineeUserName = traineeRepository.findById(id).get();
        if (traineeUserName.getUsername().equals(requestDTO.getUserName()) && traineeUserName.getIsActive() && !traineeUserName.getIsAssigned()) {
            return TraineesTrainerActiveAndNotAssignedResponseDTO
                    .builder()
                    .trainers(TrainerMapper.INSTANCE.toDtos(traineeUserName.getTrainers()))
                    .build();
        } else {
            throw new RuntimeException("Not available");
        }
    }

}
