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
                new Movie("Hugo", "Scorsese",false),
                new Movie("Silence", "Scorsese",false),
                new Movie("Kill Bill", "Tarantino",false),
                new Movie("Pulp Fiction", "Tarantino",false)
        );
        List scorseseMovies = List.of(
                new Movie("Hugo", "Scorsese",false),
                new Movie("Silence", "Scorsese",false)
        );
        movieRepository = new InMemoryMovieRepository(movies);
        movieService = new MovieService(movieRepository);
        //when
        //then
        assertEquals(movieService.moviesByDirector("Scorsese",10).size(), scorseseMovies.size());

    }
}
