package com.consume.save.controller;

import com.consume.save.commons.exception.UnauthorizedException;
import com.consume.save.commons.responseDto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiControllerAdvance {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> badResquestMethodValidator (MethodArgumentNotValidException e){
        List<String> messages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(ErrorResponse.builder()
                .message(messages.toString())
                .status(404)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedException (UnauthorizedException e){
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .status(401)
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
