package com.proto.app.exception;

import com.proto.app.model.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleExceptions(ConstraintViolationException exception, WebRequest webRequest) {
        GenericResponse response = new GenericResponse(null,ErrorType.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleExceptions(BusinessException exception, WebRequest webRequest) {
        GenericResponse response = new GenericResponse(null,exception.getErrorType().name());

        HttpStatus status = null;
        switch (exception.getErrorType()){
            case NOT_FOUND:
                status =HttpStatus.NOT_FOUND;
                break;
            case CONFLICT:
                status =HttpStatus.CONFLICT;
                break;
            default:
                status=HttpStatus.OK;
                break;
        }
        return new ResponseEntity<>(response, status);
    }
}
