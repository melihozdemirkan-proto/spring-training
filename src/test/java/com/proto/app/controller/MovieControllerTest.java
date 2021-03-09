package com.proto.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getMoviesSuccess() throws Exception {

        mvc.perform(get("/movies")) //
                .andDo(print()) //
                .andExpect(status().isOk());
    }

    @Test
    public void getMoviesByDirectorSuccess() throws Exception {

        mvc.perform(get("/movies?director=tarantino")) //
                .andDo(print()) //
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].director").value("Tarantino"));
    }


    @Test
    public void getMoviesByNameSuccess() throws Exception {
        mvc.perform(get("/movies/hugo")) //
                .andDo(print()) //
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hugo"));
    }

}
