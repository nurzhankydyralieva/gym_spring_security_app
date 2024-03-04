package com.epam.xstack.mapper.training_mapper;

import com.epam.xstack.models.dto.training_dto.request.TrainerTrainingDTO;
import com.epam.xstack.models.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingListMapper {
    TrainingListMapper INSTANCE = Mappers.getMapper(TrainingListMapper.class);

    TrainerTrainingDTO toDto(Training training);

    Training toEntity(TrainerTrainingDTO requestDTO);

    List<TrainerTrainingDTO> toDtos(List<Training> trainings);

    List<Training> toEntities(List<TrainerTrainingDTO> requestDTOS);
}
