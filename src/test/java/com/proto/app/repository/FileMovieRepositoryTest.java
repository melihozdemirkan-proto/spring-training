package com.proto.app.repository;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class FileMovieRepositoryTest {

    private MovieRepository movieRepository;

    @Test
    public void findAllReturnsMovieList(){
        //given
        int movieCountOnFile = 4;

        movieRepository = new FileMovieRepository("movies.txt");
        //when

        //then
        assertEquals(movieCountOnFile, movieRepository.findAll().size());
    }
}
