package com.epam.xstack.models.dto.trainee_dto.response;

import com.epam.xstack.models.enums.Code;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineeOkResponseDTO {
    String response;
    Code code;
}
