package com.proto.app.model;

import lombok.Data;

@Data
public class GenericResponse {
    private final Object data;
    private final String errorCode;

}
