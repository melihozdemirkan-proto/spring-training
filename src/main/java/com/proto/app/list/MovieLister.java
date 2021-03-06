package com.proto.app.list;

import com.proto.app.find.MovieFinder;
import com.proto.app.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieLister {
    private MovieFinder finder;

    public MovieLister(@Qualifier("fileMovieFinder") @Autowired MovieFinder finder) {
        this.finder = finder;
    }

    public List<Movie> moviesDirectedBy(String director) {
        List<Movie> allMovies = finder.findAll();
        List<Movie> directorMovies = new ArrayList<>();

        for (Movie movie : allMovies) {
            if ((movie.getDirector().equalsIgnoreCase(director))) {
                directorMovies.add(movie);
            }
        }
        return directorMovies;
    }
}
