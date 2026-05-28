package org.vibe.exception;

import kotlin.io.AccessDeniedException;
import org.vibe.dto.response.ErrorResponse;
import org.vibe.enums.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors().stream()
                .map(err -> err.getField() + ":" + err.getDefaultMessage())
                .toList();

        return ErrorResponse.builder()
                .type(ResponseType.ERROR.name())
                .message(errors)
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(AccessDeniedException ex) {

        return ErrorResponse.builder()
                .type(ResponseType.ERROR.name())
                .message(List.of("You do not have permission to access this resource"))
                .build();
    }
}
