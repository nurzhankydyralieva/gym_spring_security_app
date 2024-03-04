package com.epam.xstack.models.dto.trainer_dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerTrainingsListRequestDTO {
    @NotEmpty(message = "User name should not be empty")
    String userName;
}
