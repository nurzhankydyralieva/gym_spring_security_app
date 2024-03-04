package com.epam.xstack.service.training_type_service.impl;

import com.epam.xstack.aspects.training_type.annotations.SaveTrainingTypeAspectAnnotation;
import com.epam.xstack.aspects.training_type.annotations.SelectAllTrainingTypeAspectAnnotation;
import com.epam.xstack.mapper.training_type_mapper.TrainingTypeMapper;
import com.epam.xstack.models.dto.training_type_dto.TrainingTypeDTO;
import com.epam.xstack.models.entity.TrainingType;
import com.epam.xstack.repository.TrainingTypeRepository;
import com.epam.xstack.service.training_type_service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile(value = {"local", "dev", "prod", "stg"})
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository repository;

    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    @SaveTrainingTypeAspectAnnotation
    public TrainingTypeDTO save(TrainingTypeDTO trainingTypeDTO) {
        TrainingType trainingType = trainingTypeMapper.toEntity(trainingTypeDTO);
        repository.save(trainingType);

        return trainingTypeMapper.toDto(trainingType);
    }

    @Override
    @SelectAllTrainingTypeAspectAnnotation
    public List<TrainingTypeDTO> findAll() {
        List<TrainingType> all = repository.findAll();
        return trainingTypeMapper.toDtos(all);
    }
}
