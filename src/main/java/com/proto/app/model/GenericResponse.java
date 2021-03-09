package com.proto.app.model;

import lombok.Data;

@Data
public class GenericResponse<T> {
    private final T data;
    private final String errorCode;

}
