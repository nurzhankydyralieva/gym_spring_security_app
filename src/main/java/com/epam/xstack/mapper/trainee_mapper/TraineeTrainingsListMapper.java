package com.epam.xstack.mapper.trainee_mapper;

import com.epam.xstack.models.dto.trainee_dto.request.TraineeTrainingsListRequestDTO;
import com.epam.xstack.models.entity.Trainee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeTrainingsListMapper {
    TraineeTrainingsListRequestDTO toDto(Trainee trainee);

    Trainee toEntity(TraineeTrainingsListRequestDTO requestDTO);

    List<TraineeTrainingsListRequestDTO> toDtos(List<Trainee> trainees);

    List<Trainee> toEntities(List<TraineeTrainingsListRequestDTO> requestDTOS);
}
