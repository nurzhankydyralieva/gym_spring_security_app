package com.epam.xstack.mapper.trainer_mapper;

import com.epam.xstack.models.dto.trainer_dto.request.TrainerTrainingsListRequestDTO;
import com.epam.xstack.models.entity.Trainer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerTrainingsListMapper {
    TrainerTrainingsListRequestDTO toDto(Trainer trainer);

    Trainer toEntity(TrainerTrainingsListRequestDTO requestDTO);

    List<TrainerTrainingsListRequestDTO> toDtos(List<Trainer> trainers);

    List<Trainer> toEntities(List<TrainerTrainingsListRequestDTO> requestDTOS);
}
