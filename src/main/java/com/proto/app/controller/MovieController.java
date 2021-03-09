package com.proto.app.controller;

import com.proto.app.exception.BusinessException;
import com.proto.app.model.CreateMovieRequest;
import com.proto.app.model.Movie;
import com.proto.app.model.PatchMovieRequest;
import com.proto.app.model.UpdateMovieRequest;
import com.proto.app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("movies")
    List<Movie> getMoviesBy(@RequestParam(required = false) String director, @RequestParam(required = false) String name) {
        return movieService.moviesByDirectorAndName(director, name);
    }

    @GetMapping("movies/{id}")
    Movie getMoviesById(@PathVariable(required = false) String id) throws BusinessException {
        return movieService.moviesById(id).get();
    }

    @PostMapping("movies")
    void createMovie( @RequestBody CreateMovieRequest createMovieRequest) throws BusinessException {
        movieService.createMovie(createMovieRequest);
    }

    @PutMapping("movies/{id}")
    void updateMovie(@PathVariable String id, @RequestBody UpdateMovieRequest updateMovieRequest) throws BusinessException {
        movieService.updateMovie(id, updateMovieRequest);
    }

    @PatchMapping("movies/{id}")
    void patchMovie(@PathVariable String id, @RequestBody PatchMovieRequest patchMovieRequest) throws BusinessException {
        movieService.patchMovie(id, patchMovieRequest);
    }

    @DeleteMapping("movies/{id}")
    void deleteMovie(@PathVariable String id) throws BusinessException {
        movieService.deleteMovie(id);
    }
}
