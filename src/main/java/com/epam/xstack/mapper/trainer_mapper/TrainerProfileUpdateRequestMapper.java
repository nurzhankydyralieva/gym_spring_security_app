package com.epam.xstack.mapper.trainer_mapper;

import com.epam.xstack.models.dto.trainer_dto.request.TrainerProfileUpdateRequestDTO;
import com.epam.xstack.models.entity.Trainer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerProfileUpdateRequestMapper {
    TrainerProfileUpdateRequestDTO toDto(Trainer trainer);

    Trainer toEntity(TrainerProfileUpdateRequestDTO requestDTO);

    List<TrainerProfileUpdateRequestDTO> toDtos(List<Trainer> trainers);

    List<Trainer> toEntities(List<TrainerProfileUpdateRequestDTO> requestDTOS);
}
