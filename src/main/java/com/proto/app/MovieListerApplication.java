package com.proto.app;

import com.proto.app.find.FileMovieFinder;
import com.proto.app.find.InMemoryMovieFinder;
import com.proto.app.find.MovieFinder;
import com.proto.app.list.MovieLister;
import com.proto.app.model.Movie;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackageClasses = MovieListerApplication.class)
public class MovieListerApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MovieListerApplication.class);

        MovieLister movieLister = (MovieLister) context.getBean("movieLister");
        movieLister.moviesDirectedBy("Tarantino").forEach(System.out::println);
    }


    @Bean
    public MovieFinder fileMovieFinder() {
        return new FileMovieFinder("movies.txt", 4);
    }

    @Bean
    public MovieFinder inMemoryMovieFinder() {
        return new InMemoryMovieFinder(List.of(
                new Movie("Hugo", "Scorsese"),
                new Movie("Silence", "Scorsese"),
                new Movie("Kill Bill", "Tarantino"),
                new Movie("Pulp Fiction", "Tarantino")
        ), 4);
    }

}
