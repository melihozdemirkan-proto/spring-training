package com.proto.app.service;

import com.proto.app.exception.BusinessException;
import com.proto.app.exception.ErrorType;
import com.proto.app.model.CreateMovieRequest;
import com.proto.app.model.Movie;
import com.proto.app.model.PatchMovieRequest;
import com.proto.app.model.UpdateMovieRequest;
import com.proto.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MovieService {
    @Value("${app.max-movie-per-director}")
    String maxMoviePerService;
    private final MovieRepository movieRepository;

    public List<Movie> moviesByDirectorAndName(String director, String name) {
        return movieRepository.findByDirectorAndName(director, name);
    }

    public Optional<Movie> moviesById(String id) throws BusinessException {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new BusinessException(ErrorType.NOT_FOUND));

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
        Movie movie = movieRepository.findById(id).orElseThrow(()->new BusinessException(ErrorType.NOT_FOUND));

        movie.setDirector(updateMovieRequest.getDirector());
        movie.setName(updateMovieRequest.getName());
        movie.setWatched(updateMovieRequest.isWatched());
        movieRepository.save(movie);

        printList();
    }

    public void patchMovie(String id, PatchMovieRequest patchMovieRequest) throws BusinessException {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new BusinessException(ErrorType.NOT_FOUND));

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
        movieRepository.deleteById(id);
        printList();

    }


    private void printList() {
        System.out.println("-*-*-*-*-*-*-*-*-");
        movieRepository.findAll().forEach(System.out::println);
    }
}
