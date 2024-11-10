package com.ns.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class FormErrors {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> formErrors(MethodArgumentNotValidException inputFormErrors){
        Map<String, String> errorsResponse = new HashMap<>();
        inputFormErrors.getBindingResult().getFieldErrors().forEach(errors->{
            errorsResponse.put(errors.getField(),errors.getDefaultMessage());
        });
        return errorsResponse;
    }
}
