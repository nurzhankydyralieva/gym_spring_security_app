package com.epam.xstack.service.training_service;

import com.epam.xstack.models.dto.training_dto.request.TrainingSaveRequestDTO;
import com.epam.xstack.models.dto.training_dto.response.TrainingSaveResponseDTO;

public interface TrainingService {
    TrainingSaveResponseDTO saveTraining(TrainingSaveRequestDTO requestDTO);
}
