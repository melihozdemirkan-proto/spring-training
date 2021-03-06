package com.proto.app.find;

import com.proto.app.model.Movie;

import java.util.List;

public class InMemoryMovieFinder implements MovieFinder {
    private List<Movie> movies;

    public InMemoryMovieFinder(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public List<Movie> findAll() {
        return movies;
    }

}
