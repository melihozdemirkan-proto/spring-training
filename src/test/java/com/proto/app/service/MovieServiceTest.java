package com.proto.app.service;

import com.proto.app.entity.Movie;
import com.proto.app.exception.BusinessException;
import com.proto.app.exception.ErrorType;
import com.proto.app.model.CreateMovieRequest;
import com.proto.app.model.PatchMovieRequest;
import com.proto.app.model.UpdateMovieRequest;
import com.proto.app.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static com.proto.app.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void init() {
        movieService.maxMoviePerService = "4";
    }

    @Test
    public void moviesByDirectorSuccess() {
        //given
        List scorseseMovies = List.of(
                new Movie(HUGO_ID, "Hugo", "Scorsese", false),
                new Movie(SILENCE_ID, "Silence", "Scorsese", false)
        );
        //when
        when(movieRepository.findByDirectorAndName("Scorsese",null)).thenReturn(scorseseMovies);
        //then
        //assertEquals(movieService.moviesByDirectorAndName("Scorsese", null).size(), scorseseMovies.size());
        movieService.moviesByDirectorAndName("Scorsese", null);
        verify(movieRepository).findByDirectorAndName("Scorsese",null);

    }

    @Test
    public void moviesByNameSuccess() {
        //given
        Movie movie = new Movie(HUGO_ID, "Hugo", "Scorsese", false);
        //when
        when(movieRepository.findByDirectorAndName(null,"Hugo")).thenReturn(List.of(movie));
        //then
        //assertEquals(movieService.moviesByDirectorAndName(null, "Hugo").size(), 1);
        movieService.moviesByDirectorAndName(null, "Hugo");
        verify(movieRepository).findByDirectorAndName(null,"Hugo");
    }

    @Test
    public void moviesByIdSuccess() throws BusinessException {
        //given
        Movie movie = new Movie(HUGO_ID, "Hugo", "Scorsese", false);
        //when
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));
        //then
        assertEquals(movieService.moviesById(HUGO_ID).get().getId(), HUGO_ID);

    }

    @Test
    public void createMovieSuccess() throws BusinessException {
        //given
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Kill Bill 2", "Tarantino", false);
        //when
        when(movieRepository.findByDirectorAndName(createMovieRequest.getDirector(),createMovieRequest.getName())).thenReturn(List.of());
        //then
        movieService.createMovie(createMovieRequest);
        verify(movieRepository).save(any(Movie.class));

    }

    @Test
    public void createMovieReturnsConflict() throws BusinessException {
        //given
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Psycho", "Hitchcock", false);
        Movie movie = new Movie(HUGO_ID, "Hugo", "Scorsese", false);
        //when
        when(movieRepository.findByDirectorAndName(createMovieRequest.getDirector(),createMovieRequest.getName())).thenReturn(List.of(movie));
        //then
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.createMovie(createMovieRequest));
        assertEquals(ErrorType.CONFLICT, exception.getErrorType());
    }

    @Test
    public void createMovieReturnsMaxMoviePerDirector() throws BusinessException {
        //given
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Distant", "Ceylan", false);
        //when
        when(movieRepository.findByDirectorAndName(createMovieRequest.getDirector(),createMovieRequest.getName())).thenReturn(List.of());
        when(movieRepository.findByDirectorAndName(createMovieRequest.getDirector(), null))
                .thenReturn(List.of(
                        new Movie(HUGO_ID, "Three Monkeys", "Ceylan", false),
                        new Movie(SILENCE_ID, "Small Town", "Ceylan", false),
                        new Movie(KILL_BILL_ID, "Winter Sleep", "Ceylan", false),
                        new Movie(PULP_FICTION_ID, "The Wild Pear Tree", "Ceylan", false)
                )
        );

        //then
        //Attemt to create 5th movie returns business error
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.createMovie(createMovieRequest));
        assertEquals(ErrorType.MAX_MOVIE_PER_DIRECTOR, exception.getErrorType());

    }

    @Test
    public void updateMovieSuccess() throws BusinessException {
        //given
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);
        Movie movie = new Movie(HUGO_ID, "Hugo", "Scorsese", false);
        //when
        when(movieRepository.findById(HUGO_ID)).thenReturn(Optional.of(movie));
        //then
        movieService.updateMovie(HUGO_ID, updateMovieRequest);
        movie.setWatched(true);
        verify(movieRepository).save(movie);
    }

    @Test
    public void updateMovieReturnsNotFound() throws BusinessException {
        //given
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);
        //when
        when(movieRepository.findById(NA_ID)).thenThrow(new EmptyResultDataAccessException(1));
        //then
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.updateMovie(NA_ID, updateMovieRequest));
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());

    }

    @Test
    public void patchMovieSuccess() throws BusinessException {
        //given
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);
        Movie movie = new Movie(HUGO_ID, "Hugo", "Scorsese", false);
        //when
        when(movieRepository.findById(HUGO_ID)).thenReturn(Optional.of(movie));
        //then
        movieService.patchMovie(HUGO_ID, patchMovieRequest);
        movie.setWatched(true);
        verify(movieRepository).save(movie);

    }
    @Test
    public void patchMovieReturnsNotFound() throws BusinessException {
        //given
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);
        //when
        when(movieRepository.findById(NA_ID)).thenThrow(new EmptyResultDataAccessException(1));
        //then
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.patchMovie(NA_ID, patchMovieRequest));
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());

    }
    @Test
    public void deleteMovieSuccess() throws BusinessException {
        //given
        Movie movie = new Movie(HUGO_ID, "Hugo", "Scorsese", false);
        //when
        when(movieRepository.findById(HUGO_ID)).thenReturn(Optional.of(movie));
        //then

        movieService.deleteMovie(HUGO_ID);
        verify(movieRepository).deleteById(HUGO_ID);
    }

    @Test
    public void deleteMovieReturnsNotFound() throws BusinessException {
        //given
        //when
        when(movieRepository.findById(NA_ID)).thenThrow(new EmptyResultDataAccessException(1));
        //then
        BusinessException exception = assertThrows(BusinessException.class, ()->movieService.deleteMovie(NA_ID));
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());
    }
}
