package com.proto.app.model;

public class Movie {

    public String getName() {
        return name;
    }

    private String name;

    public String getDirector() {
        return director;
    }

    private String director;

    public Movie(String name, String director) {
        this.name = name;
        this.director = director;
    }

    @Override
    public String toString() {
        return name + ", " + director;
    }
}
