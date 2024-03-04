package com.epam.xstack.service.training_service.impl;

import com.epam.xstack.aspects.training_aspects.annotations.TrainingSaveAspectAnnotation;
import com.epam.xstack.mapper.training_mapper.TrainingMapper;
import com.epam.xstack.models.dto.training_dto.request.TrainingSaveRequestDTO;
import com.epam.xstack.models.dto.training_dto.response.TrainingSaveResponseDTO;
import com.epam.xstack.models.entity.Training;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.repository.TrainingRepository;
import com.epam.xstack.service.training_service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile(value = {"local","dev","prod","stg"})
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    @Override
    @TrainingSaveAspectAnnotation
    public TrainingSaveResponseDTO saveTraining(TrainingSaveRequestDTO requestDTO) {
        Training training = trainingMapper.toEntity(requestDTO);
        trainingRepository.save(training);
        trainingMapper.toDto(training);
        return TrainingSaveResponseDTO
                .builder()
                .response("Training is saved")
                .code(Code.STATUS_200_OK)
                .build();
    }
}
