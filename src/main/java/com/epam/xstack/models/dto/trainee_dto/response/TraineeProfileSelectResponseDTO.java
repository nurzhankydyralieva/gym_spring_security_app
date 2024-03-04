package com.epam.xstack.models.dto.trainee_dto.response;

import com.epam.xstack.models.dto.trainer_dto.response.TrainerDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineeProfileSelectResponseDTO {
    String firstName;
    String lastName;
    Date dateOfBirth;
    String address;
    Boolean isActive;
    Collection<TrainerDTO> trainers;
}