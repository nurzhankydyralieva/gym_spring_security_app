package com.epam.xstack.models.dto.trainer_dto.request;

import com.epam.xstack.models.entity.TrainingType;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerProfileUpdateRequestDTO {
    @NotEmpty(message = "User name should not be empty")
    String userName;
    @NotEmpty(message = "First name should not be empty")
    String firstName;
    @NotEmpty(message = "Last name should not be empty")
    String lastName;
    TrainingType specialization;
    Boolean isActive;
}
