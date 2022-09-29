package com.holichenko.cinema.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.service.movie.IMovieService;
import com.holichenko.cinema.util.EntityGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieControllerTest.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;


    @SneakyThrows
    @DisplayName("Post movie - positive case")
    @Test
    public void whenCreateMovieThenReturnMovieDTO(){
        MovieDTO movieDTO = EntityGenerator.generateMovieRequest();
        Movie movie = EntityGenerator.generateMovie();

//        when(movieService.createMovie(any(Movie.class))).thenReturn(movieDTO);

    }
}
