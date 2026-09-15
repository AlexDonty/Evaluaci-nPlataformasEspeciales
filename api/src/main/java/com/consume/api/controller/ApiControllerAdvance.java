package com.consume.api.controller;

import com.consume.api.commons.exception.ClientException;
import com.consume.api.commons.responseDto.ErrorResponse;
import feign.FeignException;
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

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(
            FeignException ex) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ex.contentUTF8())
                .status(ex.status())
                .build(), HttpStatus.BAD_REQUEST);


    }

}
