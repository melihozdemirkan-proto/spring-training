package com.proto.app.find;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class FileMovieFinderTest {

    private MovieFinder movieFinder;

    @Test
    public void findAllReturnsMovieList(){
        //given
        int movieCountOnFile = 4;

        movieFinder = new FileMovieFinder("movies.txt");
        //when

        //then
        assertEquals(movieCountOnFile, movieFinder.findAll().size());
    }
}
