package com.proto.app.repository;

import com.proto.app.model.Movie;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MovieRepository {

    default void save(Movie movie) {
        List<Movie> movies = findAll();
        movies.remove(movie);
        movies.add(movie);
    }

    List<Movie> findAll();

    default Optional<Movie> findById(String id) {
        return findByAnd(x -> x.getId().equalsIgnoreCase(id)).findFirst();
    }

    default void deleteById(String id) {
        List<Movie> movies = findAll();
        findByAnd(x -> x.getId().equalsIgnoreCase(id)).findFirst().ifPresent(movies::remove);
    }

    default List<Movie> findByDirectorAndName(String director, String name) {
        return findBy(x -> (director==null|| x.getDirector().equalsIgnoreCase(director)) && (name==null || x.getName().equalsIgnoreCase(name)) );
    }

    default List<Movie> findByName(String name) {
        return findBy(x -> x.getName().equalsIgnoreCase(name));
    }

    private List<Movie> findBy(Predicate<Movie> predicate) {
        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    private Stream<Movie> findByAnd(Predicate<Movie> predicate) {
        return findAll().stream().filter(predicate);
    }

}

