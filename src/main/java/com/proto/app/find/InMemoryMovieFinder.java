package com.proto.app.find;

import com.proto.app.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryMovieFinder implements MovieFinder {
    private List<Movie> movies;
    private int pageSize;

    public InMemoryMovieFinder(List<Movie> movies, int pageSize) {
        this.pageSize = pageSize;
        this.movies = movies;
    }

    @Override
    public List<Movie> findAll() {
        return movies.stream().limit(pageSize).collect(Collectors.toList());
    }

}
