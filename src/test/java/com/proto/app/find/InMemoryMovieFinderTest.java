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

        int pagedMovieCountOnFile = 2;

        movieFinder = new InMemoryMovieFinder(movies,2);
        //when

        //then
        assertEquals(pagedMovieCountOnFile, movieFinder.findAll().size());
    }
}
