package com.rivaldy.orderapp.exception;

import com.rivaldy.orderapp.model.response.GeneralErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalHandlerException {

    public GeneralErrorResponse toResponse(HttpStatus status, Object errors){
        return GeneralErrorResponse.builder()
                .status(status.value())
                .errors(errors)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest()
                .body(toResponse(HttpStatus.BAD_REQUEST, errors));
    }

    @ExceptionHandler(InsufficientException.class)
    public ResponseEntity<GeneralErrorResponse> handleInsufficientException(InsufficientException ex){

        return new ResponseEntity<>(toResponse(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<GeneralErrorResponse> handleDataNotFoundException(DataNotFoundException ex){

        return new ResponseEntity<>(toResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
