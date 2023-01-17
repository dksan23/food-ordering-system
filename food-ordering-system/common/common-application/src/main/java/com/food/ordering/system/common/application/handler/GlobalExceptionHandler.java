package com.food.ordering.system.common.application.handler;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.rmi.server.ExportException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception e)
    {
        return ErrorDTO.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("UnexpectedError").build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO validationException(ValidationException e)
    {
        ErrorDTO errorDTO;
        if( e instanceof ConstraintViolationException)
        {
            String violations = extractViolationsFromException((ConstraintViolationException) e);

            errorDTO = ErrorDTO.builder().message(violations).build()
        }
        else
        {
            String exceptionMessage = e.getMessage();

            errorDTO = ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(exceptionMessage).build()
        }

        return errorDTO;
    }

    private String extractViolationsFromException(ConstraintViolationException e) {

         return e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                 .collect(Collectors.joining("--"));
    }
}
