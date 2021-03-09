package com.proto.app.service;

import com.proto.app.model.Movie;
import com.proto.app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieService {
    private MovieRepository finder;

    public MovieService(@Qualifier("inMemoryMovieRepository") @Autowired MovieRepository finder) {
        this.finder = finder;
    }

    public List<Movie> moviesByDirector(String director, int pageSize) {
        return director == null ? finder.findAll() : finder.findByDirector(director, pageSize);
    }

    public List<Movie> moviesByName(String name, int pageSize) {
        return name == null ? finder.findAll() : finder.findByName(name, pageSize);
    }
}
