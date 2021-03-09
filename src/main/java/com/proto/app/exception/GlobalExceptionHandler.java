package com.proto.app.exception;

import com.proto.app.model.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception occured.", ex);
        GenericResponse response = new GenericResponse(null,ErrorType.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest webRequest) {

        log.error("Exception occured.", exception);
        GenericResponse response = new GenericResponse(null,ErrorType.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleExceptions(BusinessException exception, WebRequest webRequest) {
        log.error("Exception occured.", exception);

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
