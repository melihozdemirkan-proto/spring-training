package com.proto.app;

import com.proto.app.model.Movie;
import com.proto.app.repository.InMemoryMovieRepository;
import com.proto.app.repository.MovieRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SpringBootApplication
public class MovieListerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieListerApplication.class, args);
    }

    @Bean
    public MovieRepository inMemoryMovieRepository() {
        return new InMemoryMovieRepository(List.of(
                new Movie("Hugo", "Scorsese",false),
                new Movie("Silence", "Scorsese",false),
                new Movie("Kill Bill", "Tarantino",false),
                new Movie("Pulp Fiction", "Tarantino",false)
        ));
    }

}
