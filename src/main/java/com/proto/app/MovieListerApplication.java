package com.proto.app;

import com.proto.app.model.Movie;
import com.proto.app.repository.InMemoryMovieRepository;
import com.proto.app.repository.MovieRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@SpringBootApplication
public class MovieListerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieListerApplication.class, args);
    }

    @Bean
    public MovieRepository inMemoryMovieRepository() {
       List<Movie> movieList = new ArrayList<>();
        movieList.addAll(List.of(
                new Movie("489174a0-b30f-4c1c-a963-6339706ac2a6","Hugo", "Scorsese",false),
                new Movie("e602bf3b-7df5-4b34-8bea-0cb4170a60ba","Silence", "Scorsese",false),
                new Movie("a825a9e5-c451-4fd2-8ed0-f868fb376a40","Kill Bill", "Tarantino",false),
                new Movie("82abf600-24af-4895-a91f-ace636ab89b3","Pulp Fiction", "Tarantino",false)
        ));
        return new InMemoryMovieRepository(movieList);
    }

}
