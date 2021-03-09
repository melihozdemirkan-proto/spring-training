package com.proto.app.repository;

import com.proto.app.model.Movie;

import java.util.List;

public class InMemoryMovieRepository implements MovieRepository {
    private List<Movie> movies;

    public InMemoryMovieRepository(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public List<Movie> findAll() {
        return movies;
    }

}
