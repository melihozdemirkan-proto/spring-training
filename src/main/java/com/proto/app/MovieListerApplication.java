package com.proto.app;

import com.proto.app.model.Movie;
import com.proto.app.service.find.FileMovieFinder;
import com.proto.app.service.find.InMemoryMovieFinder;
import com.proto.app.service.find.MovieFinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackageClasses = MovieListerApplication.class)
@SpringBootApplication
public class MovieListerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieListerApplication.class, args);
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
