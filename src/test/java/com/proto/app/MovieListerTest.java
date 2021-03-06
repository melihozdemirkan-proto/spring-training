package com.proto.app;

import com.proto.app.find.InMemoryMovieFinder;
import com.proto.app.find.MovieFinder;
import com.proto.app.model.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieListerTest {

    private MovieLister movieLister;
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
        movieFinder = new InMemoryMovieFinder(movies);
        movieLister = new MovieLister(movieFinder);
        //when
        //then
        assertEquals(movieLister.moviesDirectedBy("Scorsese").size(), scorseseMovies.size());

    }
}
