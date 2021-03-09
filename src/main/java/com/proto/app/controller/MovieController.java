package com.proto.app.controller;

import com.proto.app.exception.BusinessException;
import com.proto.app.model.CreateMovieRequest;
import com.proto.app.model.Movie;
import com.proto.app.model.PatchMovieRequest;
import com.proto.app.model.UpdateMovieRequest;
import com.proto.app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
@Validated
public class MovieController {
    private final MovieService movieService;

    private final static String uuidRegex = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";

    @GetMapping("movies")
    List<Movie> getMoviesBy(@RequestParam(required = false) String director, @RequestParam(required = false) String name) {
        return movieService.moviesByDirectorAndName(director, name);
    }

    @GetMapping("movies/{id}")
    Movie getMoviesById(@PathVariable(required = false)  @Pattern(regexp = uuidRegex) String id) throws BusinessException {
        return movieService.moviesById(id).get();
    }

    @PostMapping("movies")
    void createMovie( @RequestBody @Valid CreateMovieRequest createMovieRequest) throws BusinessException {
        movieService.createMovie(createMovieRequest);
    }

    @PutMapping("movies/{id}")
    void updateMovie(@PathVariable @Pattern(regexp = uuidRegex) String id, @RequestBody @Valid UpdateMovieRequest updateMovieRequest) throws BusinessException {
        movieService.updateMovie(id, updateMovieRequest);
    }

    @PatchMapping("movies/{id}")
    void patchMovie(@PathVariable @Pattern(regexp = uuidRegex) String id, @Valid @RequestBody PatchMovieRequest patchMovieRequest) throws BusinessException {
        movieService.patchMovie(id, patchMovieRequest);
    }

    @DeleteMapping("movies/{id}")
    void deleteMovie(@PathVariable @Pattern(regexp = uuidRegex) String id) throws BusinessException {
        movieService.deleteMovie(id);
    }
}
