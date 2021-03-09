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
                new Movie("489174a0-b30f-4c1c-a963-6339706ac2a6","Hugo", "Scorsese",false),
                new Movie("e602bf3b-7df5-4b34-8bea-0cb4170a60ba","Silence", "Scorsese",false),
                new Movie("a825a9e5-c451-4fd2-8ed0-f868fb376a40","Kill Bill", "Tarantino",false),
                new Movie("82abf600-24af-4895-a91f-ace636ab89b3","Pulp Fiction", "Tarantino",false)
        );

        int movieCountOnFile = 4;

        movieRepository = new InMemoryMovieRepository(movies);
        //when

        //then
        assertEquals(movieCountOnFile, movieRepository.findAll().size());
    }
}
