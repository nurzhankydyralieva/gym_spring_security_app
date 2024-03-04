package com.epam.xstack.mapper.trainer_mapper;

import com.epam.xstack.models.dto.trainer_dto.response.TrainerDTO;
import com.epam.xstack.models.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

    TrainerDTO toDto(Trainer trainer);

    Trainer toEntity(TrainerDTO requestDTO);

    Collection<TrainerDTO> toDtos(Collection<Trainer> trainers);

    Collection<Trainer> toEntities(Collection<TrainerDTO> requestDTOS);
}