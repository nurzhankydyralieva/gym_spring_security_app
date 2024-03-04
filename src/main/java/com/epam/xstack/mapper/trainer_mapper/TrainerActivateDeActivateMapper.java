package com.epam.xstack.mapper.trainer_mapper;

import com.epam.xstack.models.dto.trainer_dto.request.TrainerActivateDeActivateDTO;
import com.epam.xstack.models.entity.Trainer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TrainerActivateDeActivateMapper {
    TrainerActivateDeActivateDTO toDto(Trainer trainer);

    Trainer toEntity(TrainerActivateDeActivateDTO dto);
}
