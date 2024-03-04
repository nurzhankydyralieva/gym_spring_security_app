package com.epam.xstack.exceptions.exception;

import com.epam.xstack.exceptions.error_models.Error;
import com.epam.xstack.exceptions.error_models.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.info("User already exists  - Exception: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                .code(ex.getCodeStatus())
                .message(ex.getMessage())
                .build()).build(), ex.getHttpStatus());
    }

    @ExceptionHandler(UserNameOrPasswordNotCorrectException.class)
    public ResponseEntity<ErrorResponse> handleUserNotCorrectException(UserNameOrPasswordNotCorrectException ex) {
        log.info("User name or password not correct -  Exception: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                .code(ex.getCodeStatus())
                .message(ex.getMessage())
                .build()).build(), ex.getHttpStatus());
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserIdNotExistsException(UserIdNotFoundException ex) {
        log.info("User Id not exists in database -  Exception: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                .code(ex.getCodeStatus())
                .message(ex.getMessage())
                .build()).build(), ex.getHttpStatus());
    }

    @ExceptionHandler(NullNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNullNotAllowedException(NullNotAllowedException ex) {
        log.info("Null not allowed -  Exception: {}", ex.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                .code(ex.getCodeStatus())
                .message(ex.getMessage())
                .build()).build(), ex.getHttpStatus());
    }
}
