package com.epam.xstack.models.dto.authentication_dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDTO  {
    @NotNull(message = "User name field should not be null")
    String userName;
    @NotEmpty(message = "Password field should not be empty")
    String password;

}