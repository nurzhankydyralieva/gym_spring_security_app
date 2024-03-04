package com.epam.xstack.mapper.trainee_mapper;

import com.epam.xstack.models.dto.trainee_dto.request.TraineeActivateDeActivateDTO;
import com.epam.xstack.models.entity.Trainee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TraineeActivateDeActivateMapper {
    TraineeActivateDeActivateDTO toDto(Trainee trainee);

    Trainee toEntity(TraineeActivateDeActivateDTO dto);
}
