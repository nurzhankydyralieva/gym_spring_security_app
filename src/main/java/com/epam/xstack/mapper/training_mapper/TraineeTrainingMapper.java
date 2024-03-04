package com.epam.xstack.mapper.training_mapper;

import com.epam.xstack.models.dto.training_dto.request.TraineeTrainingDTO;
import com.epam.xstack.models.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeTrainingMapper {

    TraineeTrainingMapper INSTANCE = Mappers.getMapper(TraineeTrainingMapper.class);

    TraineeTrainingDTO toDto(Training training);

    Training toEntity(TraineeTrainingDTO requestDTO);


    List<TraineeTrainingDTO> toDtos(List<Training> trainees);


    List<Training> toEntities(List<TraineeTrainingDTO> requestDTOS);
}
