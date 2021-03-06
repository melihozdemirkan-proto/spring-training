package com.proto.app;

import com.proto.app.find.FileMovieFinder;
import com.proto.app.find.InMemoryMovieFinder;
import com.proto.app.find.MovieFinder;
import com.proto.app.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieLister {
    private MovieFinder finder;

    public MovieLister(MovieFinder finder) {
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

    public static void main(String[] args) {
        MovieLister movieLister = initMovieLister();
        movieLister.moviesDirectedBy("Tarantino").forEach(System.out::println);
    }

    private static MovieLister initMovieLister() {
        //Prepare movies
        /*
        List movies = List.of(
                new Movie("Hugo", "Scorsese"),
                new Movie("Silence", "Scorsese"),
                new Movie("Kill Bill", "Tarantino"),
                new Movie("Pulp Fiction", "Tarantino")
        );
         */

        MovieFinder movieFinder = new FileMovieFinder("movies.txt",4);

        return new MovieLister(movieFinder);
    }
}
