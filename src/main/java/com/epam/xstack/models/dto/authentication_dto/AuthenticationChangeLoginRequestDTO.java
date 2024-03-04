package com.epam.xstack.models.dto.authentication_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationChangeLoginRequestDTO {
    @NotEmpty(message = "User name should not be empty")
    String userName;
    @NotBlank(message = "Old password is required")
    String currentPassword;
    @NotBlank(message = "New password is required")
    String newPassword;

}
