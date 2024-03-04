
package com.epam.xstack.models.dto.trainer_dto.response;

import com.epam.xstack.models.entity.TrainingType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerDTO {
    String userName;
    String firstName;
    String lastName;
    TrainingType specialization;
}
