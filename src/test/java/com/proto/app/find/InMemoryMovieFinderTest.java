package com.proto.app.find;

import com.proto.app.model.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InMemoryMovieFinderTest {

    private MovieFinder movieFinder;

    @Test
    public void findAllReturnsMovieList(){
        //given
        List movies = List.of(
                new Movie("Hugo", "Scorsese"),
                new Movie("Silence", "Scorsese"),
                new Movie("Kill Bill", "Tarantino"),
                new Movie("Pulp Fiction", "Tarantino")
        );

        movieFinder = new InMemoryMovieFinder(movies);
        //when

        //then
        assertEquals(movies.size(), movieFinder.findAll().size());
    }
}
