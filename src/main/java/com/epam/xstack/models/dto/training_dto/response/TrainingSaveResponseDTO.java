package com.epam.xstack.models.dto.training_dto.response;

import com.epam.xstack.models.enums.Code;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingSaveResponseDTO {
    String response;
    Code code;
}
