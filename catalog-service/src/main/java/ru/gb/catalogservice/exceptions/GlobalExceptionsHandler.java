package ru.gb.catalogservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(new AppError("RESOURCE_NOT_FOUND",e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<AppError> handleIncorrectFilterParametrException(IncorrectFilterParametrException e){
        return new ResponseEntity<>(new AppError("INCORRECT_FILTER_PARAMETR",e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}