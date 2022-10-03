package com.holichenko.cinema.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import com.holichenko.cinema.domain.mapper.MovieMapper;
import com.holichenko.cinema.service.movie.IMovieService;
import com.holichenko.cinema.util.EntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMovieService movieService;

    @MockBean
    private MovieMapper movieMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("Post movie - positive case")
    @Test
    public void whenCreateMovieThenReturnMovieDTO() throws Exception {
        //given
        MovieDTO movieDTO = EntityGenerator.generateMovieResponse();
        given(movieService.createMovie(any(Movie.class))).willReturn(movieDTO);
        given(movieMapper.mapToEntity(any(MovieDTO.class))).willReturn(EntityGenerator.generateMovie(movieDTO));

        //when //then
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(MovieDTO.Fields.id).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.title).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.startTime).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.duration).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.hall).isNotEmpty());
    }

    @DisplayName("Post movie - negative case")
    @Test
    public void whenCreateMovieWithNonValidBodyThenThrowException() throws Exception {
        //given
        MovieDTO movieDTO = EntityGenerator.generateNonValidMovieDTO();

        //when //then
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[\"Title mustn't be blank\"]"));

    }

    @DisplayName("Put movie - positive case")
    @Test
    public void whenUpdateMovieThenReturnMovieDTO() throws Exception {
        //given
        MovieDTO movieDTO = EntityGenerator.generateMovieResponse();
        given(movieService.updateMovie(any(Movie.class), anyLong())).willReturn(movieDTO);
        given(movieMapper.mapToEntity(any(MovieDTO.class))).willReturn(EntityGenerator.generateMovie(movieDTO));

        //when //then
        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(MovieDTO.Fields.id).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.title).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.startTime).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.duration).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.hall).isNotEmpty());
    }

    @DisplayName("Put movie - negative case")
    @Test
    public void whenUpdateMovieThenThrowException() throws Exception {
        //given
        MovieDTO movieDTO = EntityGenerator.generateMovieResponse();
        given(movieService.updateMovie(any(Movie.class), anyLong())).willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        given(movieMapper.mapToEntity(any(MovieDTO.class))).willReturn(EntityGenerator.generateMovie(movieDTO));

        //when //then
        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @DisplayName("Delete movie - positive case")
    @Test
    public void whenDeleteMovieByIdThenNothing() throws Exception {
        //given
        doNothing().when(movieService).deleteById(anyLong());

        //when //then
        mockMvc.perform(delete("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Find movie by id - positive case")
    @Test
    public void whenFindMovieByIdThenReturnMovieDTO() throws Exception {
        //given
        given(movieService.findById(anyLong())).willReturn(EntityGenerator.generateMovieResponse());

        //when //then
        mockMvc.perform(get("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(MovieDTO.Fields.id).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.title).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.startTime).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.duration).isNotEmpty())
                .andExpect(jsonPath(MovieDTO.Fields.hall).isNotEmpty());
    }

    @DisplayName("Find movie by id - negative case")
    @Test
    public void whenFindMovieByIdThenThrowException() throws Exception {
        //given
        given(movieService.findById(anyLong())).willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        //when //then
        mockMvc.perform(get("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @DisplayName("Find all movies - positive case")
    @Test
    public void whenFindAllMoviesThenReturnPaginatedMovieDTOList() throws Exception {
        //given
        MovieSearchCriteria searchCriteria = EntityGenerator.generateMovieSearchCriteria();
        given(movieService.findAll(any(MovieSearchCriteria.class), any(Pageable.class))).willReturn(EntityGenerator.generatePaginatedMovies());

        //when //then
        mockMvc.perform(post("/movies/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchCriteria)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content").hasJsonPath())
                .andExpect(jsonPath("content", hasSize(20)))
                .andExpect(jsonPath("numberOfElements").value(20))
                .andExpect(jsonPath("totalPages").value(1));
    }
}
