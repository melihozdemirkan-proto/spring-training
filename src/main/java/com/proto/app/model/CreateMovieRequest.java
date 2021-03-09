package com.proto.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CreateMovieRequest {
    @NotBlank @Size(min=1, max = 100)
    private String name;
    @NotBlank @Size(min=1, max = 100)
    private String director;
    @NotNull
    private boolean watched;
}
