package com.epam.xstack.models.dto.training_type_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingTypeDTO {
    Long id;
    @NotBlank
    String trainingType;
}
