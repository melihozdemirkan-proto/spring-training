package com.proto.app.controller;

import com.proto.app.service.MovieService;
import com.proto.app.model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class MovieController {
    private MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movies")
    List<Movie> getMovies(){
        //return movieLister.moviesDirectedBy(null).stream().map(Movie::toString).collect(Collectors.joining(","));
        return movieService.moviesDirectedBy(null);
    }

}
