package com.proto.app.service;

import com.proto.app.entity.Movie;
import com.proto.app.exception.BusinessException;
import com.proto.app.exception.ErrorType;
import com.proto.app.model.CreateMovieRequest;
import com.proto.app.model.PatchMovieRequest;
import com.proto.app.model.UpdateMovieRequest;
import com.proto.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequestScope
@RequiredArgsConstructor
public class MovieService {
    @Value("${app.max-movie-per-director}")
    String maxMoviePerService;
    private final MovieRepository movieRepository;
    private final LogService logService;

    public List<Movie> moviesByDirectorAndName(String director, String name) {
        return movieRepository.findByDirectorAndName(director, name);
    }

    public Optional<Movie> moviesById(String id) throws BusinessException {
        logService.log("moviesById start");
        Movie movie = movieRepository.findById(id).orElseThrow(()->new BusinessException(ErrorType.NOT_FOUND));
        logService.log("moviesById end");

        return Optional.ofNullable(movie);
    }

    public void createMovie(CreateMovieRequest createMovieRequest) throws BusinessException {
        if(!movieRepository.findByDirectorAndName(createMovieRequest.getDirector(),createMovieRequest.getName()).isEmpty()){
            throw new BusinessException(ErrorType.CONFLICT);
        }

        if (movieRepository.findByDirectorAndName(createMovieRequest.getDirector(), null).size() == Integer.valueOf(maxMoviePerService)) {
            throw new BusinessException(ErrorType.MAX_MOVIE_PER_DIRECTOR);
        }
        Movie movie = new Movie(UUID.randomUUID().toString(), createMovieRequest.getName(), createMovieRequest.getDirector(), createMovieRequest.isWatched());
        movieRepository.save(movie);
        printList();
    }

    public void updateMovie(String id, UpdateMovieRequest updateMovieRequest) throws BusinessException {
        Movie movie = checkIfMovieExists(id);

        movie.setDirector(updateMovieRequest.getDirector());
        movie.setName(updateMovieRequest.getName());
        movie.setWatched(updateMovieRequest.isWatched());
        movieRepository.save(movie);

        printList();
    }

    public void patchMovie(String id, PatchMovieRequest patchMovieRequest) throws BusinessException {
        Movie movie = checkIfMovieExists(id);

        if (StringUtils.hasLength(patchMovieRequest.getDirector())) {
            movie.setDirector(patchMovieRequest.getDirector());
        }
        if (StringUtils.hasLength(patchMovieRequest.getName())) {
            movie.setName(patchMovieRequest.getName());
        }
        if (patchMovieRequest.isWatched() != movie.isWatched()) {
            movie.setWatched(patchMovieRequest.isWatched());
        }
        movieRepository.save(movie);

        printList();
    }

    public void deleteMovie(String id) throws BusinessException {
        checkIfMovieExists(id);
        movieRepository.deleteById(id);
        printList();

    }

    private Movie checkIfMovieExists(String id) throws BusinessException {
        return movieRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorType.NOT_FOUND));
    }

    private void printList() {
        System.out.println("-*-*-*-*-*-*-*-*-");
        movieRepository.findAll().forEach(System.out::println);
    }
}
