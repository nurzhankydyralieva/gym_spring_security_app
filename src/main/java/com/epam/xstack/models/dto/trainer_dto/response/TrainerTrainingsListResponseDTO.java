package com.epam.xstack.models.dto.trainer_dto.response;

import com.epam.xstack.models.dto.training_dto.request.TrainerTrainingDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerTrainingsListResponseDTO {
    List<TrainerTrainingDTO> trainings;
}
