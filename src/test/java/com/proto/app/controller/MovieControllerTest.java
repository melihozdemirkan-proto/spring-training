package com.proto.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proto.app.model.CreateMovieRequest;
import com.proto.app.model.Movie;
import com.proto.app.model.PatchMovieRequest;
import com.proto.app.model.UpdateMovieRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static com.proto.app.TestHelper.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getMoviesSuccess() throws Exception {

        mvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getMoviesByDirectorSuccess() throws Exception {

        mvc.perform(get("/movies?director=tarantino"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].director").value("Tarantino"));
    }


    @Test
    public void getMoviesByNameSuccess() throws Exception {
        mvc.perform(get("/movies/?name=hugo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hugo"));
    }

    @Test
    public void createMovieSuccess() throws Exception {
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Kill Bill 2", "Tarantino", false);

        mvc.perform(
                post("/movies")
                        .content(mapper.writeValueAsString(createMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createMovieReturnsBadRequest() throws Exception {
        CreateMovieRequest createMovieRequest = new CreateMovieRequest(null, "Tarantino", false);
        mvc.perform(
                post("/movies")
                        .content(mapper.writeValueAsString(createMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMovieReturnsConflict() throws Exception {
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Matrix", "Wachowski Brothers", false);
        //1st
        mvc.perform(post("/movies").content(mapper.writeValueAsString(createMovieRequest)).header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
        //2nd
        mvc.perform(post("/movies").content(mapper.writeValueAsString(createMovieRequest.withName("Matrix Reloaded"))).header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
        //3rd
        mvc.perform(post("/movies").content(mapper.writeValueAsString(createMovieRequest.withName("Matrix Revolutions"))).header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
        //4th
        mvc.perform(post("/movies").content(mapper.writeValueAsString(createMovieRequest.withName("Matrix 4"))).header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
        //4th
        mvc.perform(post("/movies").content(mapper.writeValueAsString(createMovieRequest.withName("Sense 8"))).header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value("MAX_MOVIE_PER_DIRECTOR"));

    }

    @Test
    public void updateMovieSuccess() throws Exception {
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);

        mvc.perform(
                put("/movies/"+HUGO_ID)
                        .content(mapper.writeValueAsString(updateMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateMovieReturnsNotFound() throws Exception {
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);

        mvc.perform(
                put("/movies/"+NA_ID)
                        .content(mapper.writeValueAsString(updateMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateMovieReturnsBadRequest() throws Exception {
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest("Hugo", "Scorsese", true);

        mvc.perform(
                put("/movies/"+INVALID_ID)
                        .content(mapper.writeValueAsString(updateMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        updateMovieRequest.setName(null);
        mvc.perform(
                put("/movies/"+INVALID_ID)
                        .content(mapper.writeValueAsString(updateMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void patchMovieSuccess() throws Exception {
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);

        mvc.perform(
                patch("/movies/"+HUGO_ID)
                        .content(mapper.writeValueAsString(patchMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void patchMovieReturnsNotFound() throws Exception {
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);

        mvc.perform(
                patch("/movies/"+NA_ID)
                        .content(mapper.writeValueAsString(patchMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void patchMovieReturnsBadRequest() throws Exception {
        PatchMovieRequest patchMovieRequest = new PatchMovieRequest(null,null, true);

        mvc.perform(
                patch("/movies/"+INVALID_ID)
                        .content(mapper.writeValueAsString(patchMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteMovieSuccess() throws Exception {
        CreateMovieRequest createMovieRequest = new CreateMovieRequest("Kill Bill 3", "Tarantino", false);
        //create
        mvc.perform(
                post("/movies")
                        .content(mapper.writeValueAsString(createMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk());
        //get

        String responseJson = mvc.perform(
                get("/movies/?name=kill bill 3")
                        .content(mapper.writeValueAsString(createMovieRequest))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Movie[] movies = mapper.readValue(responseJson, Movie[].class);


        mvc.perform(
                delete("/movies/"+movies[0].getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMovieReturnsNotFound() throws Exception {

        mvc.perform(
                delete("/movies/"+NA_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMovieReturnsBadRequest() throws Exception {

        mvc.perform(
                delete("/movies/"+INVALID_ID))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
