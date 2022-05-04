package com.arthurcampolina.ToDO.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound (NotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError();
        error.setStatus(status.value());
        error.setError("Entity not found!");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> database (DataBaseException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setStatus(status.value());
        error.setError("Database exception");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
