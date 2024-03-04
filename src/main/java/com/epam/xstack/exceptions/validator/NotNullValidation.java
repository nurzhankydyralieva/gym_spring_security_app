package com.epam.xstack.exceptions.validator;

import com.epam.xstack.exceptions.exception.NullNotAllowedException;
import com.epam.xstack.models.enums.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Slf4j
@Service
public class NotNullValidation {
    public void nullValidation(BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldErrors()
                    .forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
            throw NullNotAllowedException
                    .builder()
                    .codeStatus(Code.NULL_NOT_ALLOWED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Fields should not be empty or null")
                    .build();
        }
    }

    public void userNotNullValidation(BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldErrors()
                    .forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
            throw NullNotAllowedException
                    .builder()
                    .codeStatus(Code.USER_NAME_SHOULD_NOT_BE_EMPTY)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Fields should not be empty or null")
                    .build();
        }
    }
}
