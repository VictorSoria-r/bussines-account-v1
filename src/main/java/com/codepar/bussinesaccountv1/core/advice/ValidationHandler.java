package com.codepar.bussinesaccountv1.core.advice;

import com.codepar.bussinesaccountv1.core.exception.CoderPadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCoderPadException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse();
        HttpStatus finalStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if  (ex instanceof CoderPadException coderPadException) {
            response.setMessage(coderPadException.getMessage());
            finalStatus = coderPadException.getStatus();
        }
        return new ResponseEntity<>(response,finalStatus);
    }
}
