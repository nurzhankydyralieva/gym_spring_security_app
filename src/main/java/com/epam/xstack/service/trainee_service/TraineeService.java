package com.epam.xstack.service.trainee_service;

import com.epam.xstack.models.dto.trainee_dto.request.*;
import com.epam.xstack.models.dto.trainee_dto.response.*;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerActivateDeActivateDTO;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerProfileUpdateRequestDTO;
import com.epam.xstack.models.dto.trainer_dto.request.TrainerTrainingsListRequestDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerOkResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerProfileUpdateResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerTrainingsListResponseDTO;

import java.util.UUID;

public interface TraineeService {
    TraineeRegistrationResponseDTO registerTrainee(TraineeRegistrationRequestDTO request);
    TraineeProfileSelectResponseDTO selectTraineeProfileByUserName(UUID id, TraineeProfileSelectRequestDTO requestDTO);
    TraineeProfileUpdateResponseDTO updateTraineeProfile(UUID id, TraineeProfileUpdateRequestDTO requestDTO);

    TraineeOkResponseDTO activateDe_ActivateTrainee(UUID id, TraineeActivateDeActivateDTO dto);

    TraineeOkResponseDTO deleteTraineeByUserName(UUID id, TraineeProfileSelectRequestDTO requestDTO);

    TraineeTrainingsListResponseDTO selectTraineeTrainingsList(UUID id, TraineeTrainingsListRequestDTO requestDTO);

    TraineesTrainerListUpdateResponseDTO updateTraineesTrainerList(UUID id, TraineesTrainerListUpdateRequestDTO requestDTO);

    TraineesTrainerActiveAndNotAssignedResponseDTO selectNotAssignedOnTraineeActiveTrainers(UUID id, TraineesTrainerActiveAndNotAssignedRequestDTO requestDTO);
}
