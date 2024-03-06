package com.epam.xstack.service.trainer_service.impl;

import com.epam.xstack.actuators.prometheuses.UserSessionMetrics;
import com.epam.xstack.aspects.trainer_aspects.authentication_aspects.annotations.*;
import com.epam.xstack.configuration.JwtService;
import com.epam.xstack.exceptions.exception.UserIdNotFoundException;
import com.epam.xstack.exceptions.exception.UserNameNotExistsException;
import com.epam.xstack.exceptions.generator.PasswordUserNameGenerator;
import com.epam.xstack.exceptions.validator.ActivationValidator;
import com.epam.xstack.exceptions.validator.SaveTokenValidation;
import com.epam.xstack.exceptions.validator.UserNameExistenceValidator;
import com.epam.xstack.mapper.trainee_mapper.TraineeMapper;
import com.epam.xstack.mapper.trainer_mapper.*;
import com.epam.xstack.mapper.training_mapper.TrainingListMapper;
import com.epam.xstack.models.dto.trainer_dto.request.*;
import com.epam.xstack.models.dto.trainer_dto.response.*;
import com.epam.xstack.models.entity.Trainer;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.models.enums.Role;
import com.epam.xstack.repository.TrainerRepository;
import com.epam.xstack.repository.UserRepository;
import com.epam.xstack.service.trainer_service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Profile(value = {"local", "dev", "prod", "stg"})
public class TrainerServiceImpl implements TrainerService {
    private final UserRepository repository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordUserNameGenerator generator;
    private final SaveTokenValidation tokenValidation;
    private final ActivationValidator checkActivation;
    private final UserNameExistenceValidator checkUserNameExistence;
    private final UserSessionMetrics userSessionMetrics;
    private AtomicInteger activeSessions = new AtomicInteger(0);
    private final TrainerProfileSelectRequestMapper getTrainerProfileRequestMapper;
    private final TrainerProfileUpdateRequestMapper updateTrainerProfileRequestMapper;
    private final TrainerActivateDeActivateMapper activateDeActivateTrainerMapper;
    private final TrainerTrainingsListMapper trainerTrainingsListMapper;
    private final TrainerRegistrationRequestMapper registrationRequestMapper;


    @Override
    @SaveTraineeAspectAnnotation
    public TrainerRegistrationResponseDTO registerTrainer(TrainerRegistrationRequestDTO requestDTO) {
        Trainer trainer = registrationRequestMapper.toEntity(requestDTO);
        String generatedPassword = generator.generateRandomPassword();
        String createdUserName = generator.generateUserName(requestDTO.getFirstName(), requestDTO.getLastName());

        var createTrainer = new Trainer();
        createTrainer.setUserName(createdUserName);
        createTrainer.setFirstName(trainer.getFirstName());
        createTrainer.setLastName(trainer.getLastName());
        createTrainer.setPassword(passwordEncoder.encode(generatedPassword));
        createTrainer.setIsActive(true);
        createTrainer.setRole(Role.TRAINER);

        checkUserNameExistence.userNameExists(createdUserName);

        var savedUser = repository.save(createTrainer);
        activeSessions.incrementAndGet();
        userSessionMetrics.incrementActiveSessions();

        var jwtToken = jwtService.generateToken(createTrainer);
        tokenValidation.saveUserToken(savedUser, jwtToken);
        return TrainerRegistrationResponseDTO.builder()
                .userName(createTrainer.getUsername())
                .password(generatedPassword)
                .build();
    }

    @Override
    @SelectTrainerProfileByUserNameAspectAnnotation
    public TrainerProfileSelectResponseDTO selectTrainerProfileByUserName(UUID id, TrainerProfileSelectRequestDTO requestDTO) {
        Trainer trainer = getTrainerProfileRequestMapper.toEntity(requestDTO);
        Trainer trainerId = trainerRepository.findById(id).get();

        if (trainerId.getUsername().equals(requestDTO.getUserName())) {
            getTrainerProfileRequestMapper.toDto(trainer);

            return TrainerProfileSelectResponseDTO
                    .builder()
                    .firstName(trainerId.getFirstName())
                    .lastName(trainerId.getLastName())
                    .specialization(trainerId.getSpecialization())
                    .isActive(trainerId.getIsActive())
                    .traineeList(TraineeMapper.INSTANCE.toDtos(trainerId.getTraineeList()))
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
    @SelectTrainerTrainingsListAspectAnnotation
    public TrainerTrainingsListResponseDTO selectTrainerTrainingsList(UUID id, TrainerTrainingsListRequestDTO requestDTO) {
        Trainer trainerId = trainerRepository.findById(id).get();
        trainerTrainingsListMapper.toEntity(requestDTO);

        if (trainerId.getUsername().equals(requestDTO.getUserName())) {
            return TrainerTrainingsListResponseDTO
                    .builder()
                    .trainings(TrainingListMapper.INSTANCE.toDtos(trainerId.getTrainings()))
                    .build();
        } else {
            throw UserIdNotFoundException.builder()
                    .codeStatus(Code.USER_ID_NOT_FOUND)
                    .message("User id:  " + trainerId.getId() + " or user name: " + trainerId.getUsername() + " not correct.")
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }
    }

    @Override
    @ActivateDe_ActivateTrainerAspectAnnotation
    public TrainerOkResponseDTO activateDe_ActivateTrainer(UUID id, TrainerActivateDeActivateDTO dto) {
        Trainer trainer = activateDeActivateTrainerMapper.toEntity(dto);
        Trainer existingTrainer = trainerRepository.findById(id).get();

        checkActivation.checkActiveOrNotTrainerActive(id, dto);

        existingTrainer.setIsActive(dto.getIsActive());
        trainerRepository.save(existingTrainer);
        activateDeActivateTrainerMapper.toDto(trainer);
        return TrainerOkResponseDTO
                .builder()
                .code(Code.STATUS_200_OK)
                .response("Activate DeActivate Trainer updated")
                .build();
    }

    @Override
    @UpdateTrainerProfileAspectAnnotation
    public TrainerProfileUpdateResponseDTO updateTrainerProfile(UUID id, TrainerProfileUpdateRequestDTO requestDTO) {
        Trainer trainer = updateTrainerProfileRequestMapper.toEntity(requestDTO);
        Trainer trainerToBeUpdated = trainerRepository.findById(id).get();
        if (trainerToBeUpdated.getId() == id) {
            trainerToBeUpdated.setFirstName(requestDTO.getFirstName());
            trainerToBeUpdated.setLastName(requestDTO.getLastName());
            trainerToBeUpdated.setIsActive(requestDTO.getIsActive());

            trainerRepository.save(trainerToBeUpdated);
            updateTrainerProfileRequestMapper.toDto(trainer);
        }

        return TrainerProfileUpdateResponseDTO
                .builder()
                .userName(trainerToBeUpdated.getUsername())
                .firstName(trainerToBeUpdated.getFirstName())
                .lastName(trainerToBeUpdated.getLastName())
                .specialization(trainerToBeUpdated.getSpecialization())
                .isActive(trainerToBeUpdated.getIsActive())
                .trainees(TraineeMapper.INSTANCE.toDtos(trainerToBeUpdated.getTraineeList()))
                .build();
    }

}
