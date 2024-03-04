package com.epam.xstack.exceptions.validator;

import com.epam.xstack.exceptions.exception.UserNameOrPasswordNotCorrectException;
import com.epam.xstack.models.dto.trainee_dto.request.TraineeActivateDeActivateDTO;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerActivateDeActivateDTO;
import com.epam.xstack.models.entity.Trainee;
import com.epam.xstack.models.entity.Trainer;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.repository.TraineeRepository;
import com.epam.xstack.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivationValidator {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    public void checkActiveOrNotTraineeActive(UUID id, TraineeActivateDeActivateDTO dto) {

        Trainee existingTrainee = traineeRepository.findById(id).get();

        if (!existingTrainee.getUsername().equals(dto.getUserName())) {
            throw UserNameOrPasswordNotCorrectException.builder()
                    .codeStatus(Code.REQUEST_VALIDATION_ERROR)
                    .message("User name " + dto.getUserName() + " not correct")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public void checkActiveOrNotTrainerActive(UUID id, TrainerActivateDeActivateDTO dto) {
        Trainer existingTrainer = trainerRepository.findById(id).get();

        if (!existingTrainer.getUsername().equals(dto.getUserName())) {
            throw UserNameOrPasswordNotCorrectException.builder()
                    .codeStatus(Code.REQUEST_VALIDATION_ERROR)
                    .message("User name " + dto.getUserName() + " not correct")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
