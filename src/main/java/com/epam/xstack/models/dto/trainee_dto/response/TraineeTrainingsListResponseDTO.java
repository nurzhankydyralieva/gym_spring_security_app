package com.epam.xstack.models.dto.trainee_dto.response;

import com.epam.xstack.models.dto.training_dto.request.TraineeTrainingDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineeTrainingsListResponseDTO {
    List<TraineeTrainingDTO> trainings;
}
