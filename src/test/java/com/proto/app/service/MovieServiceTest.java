package com.proto.app.service;

import com.proto.app.model.Movie;
import com.proto.app.repository.InMemoryMovieRepository;
import com.proto.app.repository.MovieRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieServiceTest {

    private MovieService movieService;
    private MovieRepository movieRepository;

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
        movieRepository = new InMemoryMovieRepository(movies);
        movieService = new MovieService(movieRepository);
        //when
        //then
        assertEquals(movieService.moviesByDirector("Scorsese",10).size(), scorseseMovies.size());

    }
}
