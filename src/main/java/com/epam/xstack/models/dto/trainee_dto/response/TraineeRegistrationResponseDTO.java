package com.epam.xstack.models.dto.trainee_dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineeRegistrationResponseDTO {
    String userName;
    String password;
}
