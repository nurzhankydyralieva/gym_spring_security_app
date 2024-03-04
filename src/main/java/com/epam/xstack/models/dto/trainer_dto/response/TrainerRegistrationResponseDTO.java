package com.epam.xstack.models.dto.trainer_dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerRegistrationResponseDTO {
    String userName;
    String password;
}

