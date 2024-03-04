package com.epam.xstack.models.dto.trainee_dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraineeProfileUpdateRequestDTO {
    @NotEmpty(message = "User name should not be empty")
    String userName;
    @NotEmpty(message = "First name should not be empty")
    String firstName;
    @NotEmpty(message = "Last name should not be empty")
    String lastName;
    @Past(message = "Date of birth have to be in past")
    Date dateOfBirth;
    @NotBlank(message = "Address should not be blank")
    String address;
    Boolean isActive;
}
