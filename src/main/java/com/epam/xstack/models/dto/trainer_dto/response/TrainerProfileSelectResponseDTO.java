package com.epam.xstack.models.dto.trainer_dto.response;

import com.epam.xstack.models.dto.trainee_dto.response.TraineeDTO;
import com.epam.xstack.models.entity.TrainingType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerProfileSelectResponseDTO {
    String firstName;
    String lastName;
    TrainingType specialization;
    Boolean isActive;
    List<TraineeDTO> traineeList;
}


