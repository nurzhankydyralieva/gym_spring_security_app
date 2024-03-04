package com.epam.xstack.mapper.trainer_mapper;

import com.epam.xstack.models.dto.trainer_dto.request.TrainerProfileSelectRequestDTO;
import com.epam.xstack.models.entity.Trainer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerProfileSelectRequestMapper {

    TrainerProfileSelectRequestDTO toDto(Trainer trainer);

    Trainer toEntity(TrainerProfileSelectRequestDTO requestDTO);

    List<TrainerProfileSelectRequestDTO> toDtos(List<Trainer> trainers);

    List<Trainer> toEntities(List<TrainerProfileSelectRequestDTO> requestDTOS);
}