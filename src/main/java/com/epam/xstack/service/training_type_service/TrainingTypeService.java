package com.epam.xstack.service.training_type_service;

import com.epam.xstack.models.dto.training_type_dto.TrainingTypeDTO;

import java.util.List;

public interface TrainingTypeService {
    TrainingTypeDTO save(TrainingTypeDTO trainingTypeDTO);

    List<TrainingTypeDTO> findAll();
}
