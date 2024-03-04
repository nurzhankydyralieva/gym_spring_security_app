package com.epam.xstack.mapper.trainee_mapper;

import com.epam.xstack.models.dto.trainee_dto.request.TraineesTrainerListUpdateRequestDTO;
import com.epam.xstack.models.entity.Trainee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineesTrainerListUpdateMapper {

    TraineesTrainerListUpdateRequestDTO toDto(Trainee trainee);

    Trainee toEntity(TraineesTrainerListUpdateRequestDTO requestDTO);

    List<TraineesTrainerListUpdateRequestDTO> toDtos(List<Trainee> trainees);

    List<Trainee> toEntities(List<TraineesTrainerListUpdateRequestDTO> requestDTOS);
}
