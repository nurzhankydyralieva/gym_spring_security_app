package com.epam.xstack.models.dto.authentication_dto;

import com.epam.xstack.models.enums.Code;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationChangeLoginResponseDTO {
    String response;
    Code code;
}
