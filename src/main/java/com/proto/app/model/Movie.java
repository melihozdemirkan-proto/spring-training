package com.proto.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
    private String name;
    private String director;
    private boolean watched;
}
