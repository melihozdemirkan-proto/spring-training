package com.proto.app.service;

import com.proto.app.exception.BusinessException;
import com.proto.app.exception.ErrorType;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        movieService.maxMoviePerService="4";
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
    public void moviesByIdSuccess() throws BusinessException {
        //given
        //when
        //then
        assertEquals(movieService.moviesById(HUGO_ID).get().getId(), HUGO_ID);

    }

    @Test
    public void createMovieSuccess() throws BusinessException {
        //given
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Kill Bill 2", "Tarantino", false);
        int existingSize = movieRepository.findAll().size();
        //when
        //then
        movieService.createMovie(createMovieRequest);
        assertEquals(movieRepository.findAll().size(), existingSize+1);

    }

    @Test
    public void createMovieReturnsConflict() throws BusinessException {
        //given
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Psycho", "Hitchcock", false);
        //when
        //then
        movieService.createMovie(createMovieRequest);
        //Attemt to create 4th movie returns business error
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.createMovie(createMovieRequest));
        assertEquals(ErrorType.CONFLICT, exception.getErrorType());

    }

    @Test
    public void createMovieReturnsMaxMoviePerDirector() throws BusinessException {
        //given
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Distant", "Ceylan", false);
        //when
        //then
        movieService.createMovie(createMovieRequest);
        movieService.createMovie(createMovieRequest.withName("Three Monkeys"));
        movieService.createMovie(createMovieRequest.withName("Small Town"));
        movieService.createMovie(createMovieRequest.withName("Winter Sleep"));
        //Attemt to create 4th movie returns business error
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.createMovie(createMovieRequest.withName("The Wild Pear Tree")));
        assertEquals(ErrorType.MAX_MOVIE_PER_DIRECTOR, exception.getErrorType());

    }

    @Test
    public void updateMovieSuccess() throws BusinessException {
        //given
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);
        //when
        //then
        movieService.updateMovie(HUGO_ID, updateMovieRequest);
        assertEquals(movieRepository.findById(HUGO_ID).get().isWatched(), updateMovieRequest.isWatched());

    }

    @Test
    public void updateMovieReturnsNotFound() throws BusinessException {
        //given
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);
        //when
        //then

        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.updateMovie(NA_ID, updateMovieRequest));
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());

    }

    @Test
    public void patchMovieSuccess() throws BusinessException {
        //given
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);
        //when
        //then
        movieService.patchMovie(HUGO_ID, patchMovieRequest);
        assertEquals(movieRepository.findById(HUGO_ID).get().isWatched(), patchMovieRequest.isWatched());

    }
    @Test
    public void patchMovieReturnsNotFound() throws BusinessException {
        //given
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);
        //when
        //then
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.patchMovie(NA_ID, patchMovieRequest));
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());

    }
    public void deleteMovieSuccess() throws BusinessException {
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

    @Test
    public void deleteMovieReturnsNotFound() throws BusinessException {
        //given
        //when
        //then
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.deleteMovie(NA_ID));
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());
    }
}
