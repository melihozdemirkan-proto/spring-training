package com.proto.app.controller;

import com.proto.app.model.Movie;
import com.proto.app.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movies")
    List<Movie> getMoviesByDirector(@RequestParam(required = false) String director) {
        return movieService.moviesByDirector(director, 10);
    }

    @GetMapping("movies/{name}")
    List<Movie> getMoviesByName(@PathVariable(required = false) String name) {
        return movieService.moviesByName(name, 10);
    }
}
