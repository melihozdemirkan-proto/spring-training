package com.proto.app.service.find;

import com.proto.app.service.find.FileMovieFinder;
import com.proto.app.service.find.MovieFinder;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class FileMovieFinderTest {

    private MovieFinder movieFinder;

    @Test
    public void findAllReturnsMovieList(){
        //given
        int pagedMovieCountOnFile = 2;

        movieFinder = new FileMovieFinder("movies.txt",2);
        //when

        //then
        assertEquals(pagedMovieCountOnFile, movieFinder.findAll().size());
    }
}
