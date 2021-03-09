package com.proto.app.exception;

import lombok.Data;

@Data
public class BusinessException extends Exception{
    private final ErrorType errorType;
}
