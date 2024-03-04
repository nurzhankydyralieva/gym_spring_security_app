package com.epam.xstack.models.dto.training_dto.request;

import com.epam.xstack.models.dto.trainer_dto.response.TrainerDTO;
import com.epam.xstack.models.dto.training_type_dto.TrainingTypeDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineeTrainingDTO {
    @NotEmpty(message = "Training name should not be empty")
    String trainingName;
    Date trainingDate;
    TrainingTypeDTO trainingType;

    Number trainingDuration;
    TrainerDTO trainer;
}
