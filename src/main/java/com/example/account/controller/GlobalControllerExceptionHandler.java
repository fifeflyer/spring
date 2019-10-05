package com.example.account.controller;

import com.example.account.exception.AccountNotFoundException;
import com.example.account.model.ErrorResponse;
import com.example.account.model.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public void handleAccountNotFound() {
        log.info("Account not found!");
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleValidationException(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("Bad Request - {}", e.getMessage());

        List<ValidationError> errors = e.getBindingResult().getFieldErrors().stream()
            .map(r -> ValidationError.builder()
                .attribute(r.getField())
                .reason(r.getDefaultMessage())
                .build())
            .collect(Collectors.toList());

        return ErrorResponse.builder()
            .url(request.getRequestURI())
            .queryString(request.getQueryString())
            .validation(errors)
            .parameter(e.getParameter().getParameterName())
            .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error("Exception - {}", e.getMessage());
    }
}
