package com.proto.app.service;

import com.proto.app.model.Movie;
import com.proto.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository finder;

    public List<Movie> moviesByDirector(String director, int pageSize) {
        return director == null ? finder.findAll() : finder.findByDirector(director, pageSize);
    }

    public List<Movie> moviesByName(String name, int pageSize) {
        return name == null ? finder.findAll() : finder.findByName(name, pageSize);
    }
}
