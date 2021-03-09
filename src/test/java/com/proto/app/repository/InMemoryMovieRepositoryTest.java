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
                new Movie("Hugo", "Scorsese",false),
                new Movie("Silence", "Scorsese",false),
                new Movie("Kill Bill", "Tarantino",false),
                new Movie("Pulp Fiction", "Tarantino",false)
        );

        int movieCountOnFile = 4;

        movieRepository = new InMemoryMovieRepository(movies);
        //when

        //then
        assertEquals(movieCountOnFile, movieRepository.findAll().size());
    }
}
