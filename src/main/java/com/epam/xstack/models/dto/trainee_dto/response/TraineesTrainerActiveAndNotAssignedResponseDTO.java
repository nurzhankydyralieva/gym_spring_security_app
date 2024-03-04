package com.epam.xstack.models.dto.trainee_dto.response;

import com.epam.xstack.models.dto.trainer_dto.response.TrainerDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineesTrainerActiveAndNotAssignedResponseDTO {
    Collection<TrainerDTO> trainers;
}
