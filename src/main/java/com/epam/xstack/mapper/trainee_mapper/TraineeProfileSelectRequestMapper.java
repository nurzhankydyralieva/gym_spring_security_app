package com.epam.xstack.mapper.trainee_mapper;

import com.epam.xstack.models.dto.trainee_dto.request.TraineeProfileSelectRequestDTO;
import com.epam.xstack.models.entity.Trainee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeProfileSelectRequestMapper {
    TraineeProfileSelectRequestDTO toDto(Trainee trainee);

    Trainee toEntity(TraineeProfileSelectRequestDTO requestDTO);

    List<TraineeProfileSelectRequestDTO> toDtos(List<Trainee> trainees);

    List<Trainee> toEntities(List<TraineeProfileSelectRequestDTO> requestDTOS);
}