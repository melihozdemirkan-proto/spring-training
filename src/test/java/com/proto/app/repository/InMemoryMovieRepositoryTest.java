package com.proto.app.repository;

import com.proto.app.model.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InMemoryMovieRepositoryTest {

    private MovieRepository movieRepository;

    @Test
    public void findAllReturnsMovieList(){
        //given
        List movies = List.of(
                new Movie("Hugo", "Scorsese"),
                new Movie("Silence", "Scorsese"),
                new Movie("Kill Bill", "Tarantino"),
                new Movie("Pulp Fiction", "Tarantino")
        );

        int movieCountOnFile = 4;

        movieRepository = new InMemoryMovieRepository(movies);
        //when

        //then
        assertEquals(movieCountOnFile, movieRepository.findAll().size());
    }
}
