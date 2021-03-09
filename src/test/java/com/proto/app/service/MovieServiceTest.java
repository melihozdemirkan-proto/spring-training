package com.proto.app.service;

import com.proto.app.model.CreateMovieRequest;
import com.proto.app.model.Movie;
import com.proto.app.model.PatchMovieRequest;
import com.proto.app.model.UpdateMovieRequest;
import com.proto.app.repository.InMemoryMovieRepository;
import com.proto.app.repository.MovieRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.ArrayList;
import java.util.List;

import static com.proto.app.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
public class MovieServiceTest {


    private MovieService movieService;
    private MovieRepository movieRepository;

    @BeforeAll
    public void init() {
        List movies = new ArrayList(List.of(
                new Movie(HUGO_ID, "Hugo", "Scorsese", false),
                new Movie(SILENCE_ID, "Silence", "Scorsese", false),
                new Movie(KILL_BILL_ID, "Kill Bill", "Tarantino", false),
                new Movie(PULP_FICTION_ID, "Pulp Fiction", "Tarantino", false)
        ));

        movieRepository = new InMemoryMovieRepository(movies);
        movieService = new MovieService(movieRepository);
    }


    @Test
    public void moviesByDirectorSuccess() {
        //given
        List scorseseMovies = List.of(
                new Movie(HUGO_ID, "Hugo", "Scorsese", false),
                new Movie(SILENCE_ID, "Silence", "Scorsese", false)
        );
        //when
        //then
        assertEquals(movieService.moviesByDirectorAndName("Scorsese", null).size(), scorseseMovies.size());

    }

    @Test
    public void moviesByNameSuccess() {
        //given
        //when
        //then
        assertEquals(movieService.moviesByDirectorAndName(null, "Hugo").size(), 1);

    }

    @Test
    public void moviesByIdSuccess() {
        //given
        //when
        //then
        assertEquals(movieService.moviesById(HUGO_ID).get().getId(), HUGO_ID);

    }

    @Test
    public void createMovieSuccess() {
        //given
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Kill Bill 2", "Tarantino", false);
        int existingSize = movieRepository.findAll().size();
        //when
        //then
        movieService.createMovie(createMovieRequest);
        assertEquals(movieRepository.findAll().size(), existingSize+1);

    }

    @Test
    public void updateMovieSuccess() {
        //given
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);
        //when
        //then
        movieService.updateMovie(HUGO_ID, updateMovieRequest);
        assertEquals(movieRepository.findById(HUGO_ID).get().isWatched(), updateMovieRequest.isWatched());

    }

    @Test
    public void patchMovieSuccess() {
        //given
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);
        //when
        //then
        movieService.patchMovie(HUGO_ID, patchMovieRequest);
        assertEquals(movieRepository.findById(HUGO_ID).get().isWatched(), patchMovieRequest.isWatched());

    }

    @Test
    public void deleteMovieSuccess() {
        //given
        int existingSize = movieRepository.findAll().size();
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Kill Bill 3", "Tarantino", false);
        //when
        //then
        movieService.createMovie(createMovieRequest);
        String existingId = movieRepository.findByName("Kill Bill 3").get(0).getId();
        assertEquals(movieRepository.findAll().size(), existingSize+1);

        movieService.deleteMovie(existingId);
        assertEquals(movieRepository.findAll().size(), existingSize);
    }
}
