package com.proto.app.find;

import com.proto.app.model.Movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileMovieFinder implements MovieFinder {
    private List<Movie> movies;

    public FileMovieFinder(String fileName) {
        this.movies = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                movies.add(new Movie(data[0], data[1]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> findAll() {
        return movies;
    }
}
