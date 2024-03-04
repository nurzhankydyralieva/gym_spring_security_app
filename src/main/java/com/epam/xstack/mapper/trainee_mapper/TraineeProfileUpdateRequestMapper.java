package com.epam.xstack.mapper.trainee_mapper;

import com.epam.xstack.models.dto.trainee_dto.request.TraineeProfileUpdateRequestDTO;
import com.epam.xstack.models.entity.Trainee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeProfileUpdateRequestMapper {
    TraineeProfileUpdateRequestDTO toDto(Trainee trainee);

    Trainee toEntity(TraineeProfileUpdateRequestDTO requestDTO);

    List<TraineeProfileUpdateRequestDTO> toDtos(List<Trainee> trainees);

    List<Trainee> toEntities(List<TraineeProfileUpdateRequestDTO> requestDTOS);
}
