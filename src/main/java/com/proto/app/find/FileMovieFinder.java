package com.proto.app.find;

import com.proto.app.model.Movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileMovieFinder implements MovieFinder {
    private List<Movie> movies;
    private int pageSize;

    public FileMovieFinder(String fileName,int pageSize) {
        this.pageSize = pageSize;
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
        return movies.stream().limit(pageSize).collect(Collectors.toList());
    }
}
