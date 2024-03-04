package com.epam.xstack.mapper.trainee_mapper;

import com.epam.xstack.models.dto.trainee_dto.request.TraineeRegistrationRequestDTO;
import com.epam.xstack.models.entity.Trainee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeRegistrationRequestMapper {
    TraineeRegistrationRequestDTO toDto(Trainee trainee);

    Trainee toEntity(TraineeRegistrationRequestDTO requestDTO);

    List<TraineeRegistrationRequestDTO> toDtos(List<Trainee> trainees);

    List<Trainee> toEntities(List<TraineeRegistrationRequestDTO> requestDTOS);
}
