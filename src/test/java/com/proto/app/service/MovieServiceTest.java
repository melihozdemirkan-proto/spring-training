package com.proto.app.service;

import com.proto.app.model.Movie;
import com.proto.app.service.find.InMemoryMovieFinder;
import com.proto.app.service.find.MovieFinder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieServiceTest {

    private MovieService movieService;
    private MovieFinder movieFinder;

    @Test
    public void moviesDirectedByReturnsDirectorsMovies() {
        //given
        List movies = List.of(
                new Movie("Hugo", "Scorsese"),
                new Movie("Silence", "Scorsese"),
                new Movie("Kill Bill", "Tarantino"),
                new Movie("Pulp Fiction", "Tarantino")
        );
        List scorseseMovies = List.of(
                new Movie("Hugo", "Scorsese"),
                new Movie("Silence", "Scorsese")
        );
        movieFinder = new InMemoryMovieFinder(movies,4);
        movieService = new MovieService(movieFinder);
        //when
        //then
        assertEquals(movieService.moviesDirectedBy("Scorsese").size(), scorseseMovies.size());

    }
}
