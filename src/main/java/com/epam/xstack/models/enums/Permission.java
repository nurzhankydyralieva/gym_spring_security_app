package com.epam.xstack.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    TRAINEE_READ("trainee:read"),
    TRAINEE_UPDATE("trainee:update"),
    TRAINEE_CREATE("trainee:create"),
    TRAINEE_DELETE("trainee:delete"),
    TRAINER_READ("trainer:read"),
    TRAINER_UPDATE("trainer:update"),
    TRAINER_CREATE("trainer:create"),
    TRAINER_DELETE("trainer:delete");

    @Getter
    private final String permission;
}
