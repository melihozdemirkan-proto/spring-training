package com.proto.app.service;

import com.proto.app.model.Movie;
import com.proto.app.service.find.MovieFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieService {
    private MovieFinder finder;

    public MovieService(@Qualifier("fileMovieFinder") @Autowired MovieFinder finder) {
        this.finder = finder;
    }

    public List<Movie> moviesDirectedBy(String director) {
        List<Movie> allMovies = finder.findAll();
        List<Movie> directorMovies = new ArrayList<>();

        if(director==null){
            return allMovies;
        }

        for (Movie movie : allMovies) {
            if ((movie.getDirector().equalsIgnoreCase(director))) {
                directorMovies.add(movie);
            }
        }
        return directorMovies;
    }
}
