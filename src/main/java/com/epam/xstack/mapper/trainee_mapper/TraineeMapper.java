package com.epam.xstack.mapper.trainee_mapper;

import com.epam.xstack.models.dto.trainee_dto.response.TraineeDTO;
import com.epam.xstack.models.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    TraineeMapper INSTANCE = Mappers.getMapper(TraineeMapper.class);

    TraineeDTO toDto(Trainee trainee);

    Trainee toEntity(TraineeDTO requestDTO);

    List<TraineeDTO> toDtos(List<Trainee> trainees);

    List<Trainee> toEntities(List<TraineeDTO> requestDTOS);
}
