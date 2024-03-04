package com.epam.xstack.service.trainer_service;

import com.epam.xstack.models.dto.trainer_dto.request.*;
import com.epam.xstack.models.dto.trainer_dto.response.*;

import java.util.UUID;

public interface TrainerService {
    TrainerRegistrationResponseDTO registerTrainer(TrainerRegistrationRequestDTO request);
    TrainerProfileSelectResponseDTO selectTrainerProfileByUserName(UUID id, TrainerProfileSelectRequestDTO requestDTO);
    TrainerProfileUpdateResponseDTO updateTrainerProfile(UUID id, TrainerProfileUpdateRequestDTO requestDTO);

    TrainerOkResponseDTO activateDe_ActivateTrainer(UUID id, TrainerActivateDeActivateDTO dto);

    TrainerTrainingsListResponseDTO selectTrainerTrainingsList(UUID id, TrainerTrainingsListRequestDTO requestDTO);
}
