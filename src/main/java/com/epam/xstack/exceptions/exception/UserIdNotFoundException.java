package com.epam.xstack.exceptions.exception;

import com.epam.xstack.models.enums.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserIdNotFoundException extends RuntimeException {
    private final Code codeStatus;
    private final String message;
    private final HttpStatus httpStatus;
}
