package com.proto.app.repository;

import com.proto.app.model.Movie;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface MovieRepository {

    List<Movie> findAll();

    default List<Movie> findByDirector(String director, Integer pageSize) {
        return findBy(x -> x.getDirector().equalsIgnoreCase(director), pageSize);
    }

    default List<Movie> findByName(String name, Integer pageSize) {
        return findBy(x -> x.getName().equalsIgnoreCase(name), pageSize);
    }

    private List<Movie> findBy(Predicate<Movie> predicate, int pageSize) {
        return findAll().stream().filter(predicate).limit(pageSize).collect(Collectors.toList());
    }

}

