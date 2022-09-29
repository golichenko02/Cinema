package com.holichenko.cinema.service;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import com.holichenko.cinema.domain.mapper.MovieMapper;
import com.holichenko.cinema.repository.MovieRepository;
import com.holichenko.cinema.service.movie.impl.MovieService;
import com.holichenko.cinema.util.EntityGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;
    @InjectMocks
    private MovieService movieService;


    @DisplayName("Save movie - positive case")
    @Test
    public void whenCreateMovieThenReturnMovieDTO() {
        //given
        Movie movie = EntityGenerator.generateMovie();
        MovieDTO movieDTO = EntityGenerator.generateMovieDTO(movie);
        given(movieRepository.save(any(Movie.class))).willReturn(movie);
        given(movieMapper.mapToDTO(any(Movie.class))).willReturn(movieDTO);

        //when
        MovieDTO result = movieService.createMovie(movie);

        //then
        Assertions.assertThat(result).isEqualTo(movieDTO);
    }

    @DisplayName("Update movie - negative case")
    @Test
    public void whenUpdateMovieThenThrowException() {
        //given
        Movie movie = EntityGenerator.generateMovie();
        given(movieRepository.findByIdJoinFetchOrders(any(Long.class))).willReturn(Optional.empty());

        //when
        assertThrows(HttpClientErrorException.class, () -> movieService.updateMovie(movie, movie.getId()));

        //then
        verify(movieRepository, times(1)).findByIdJoinFetchOrders(any(Long.class));
    }

    @DisplayName("Update movie - positive case")
    @Test
    public void whenUpdateMovieThenReturnMovieDTO() {
        //given
        Movie movie = Mockito.mock(Movie.class, RETURNS_DEEP_STUBS);
        MovieDTO movieDTO = EntityGenerator.generateMovieDTO(movie);
        given(movieRepository.findByIdJoinFetchOrders(any(Long.class))).willReturn(Optional.of(movie));
        given(movieMapper.mapToDTO(any(Movie.class))).willReturn(movieDTO);

        //when
        MovieDTO result = movieService.updateMovie(movie, movie.getId());

        //then
        verify(movieRepository, times(1)).findByIdJoinFetchOrders(any(Long.class));
        verify(movie, times(1)).updateMovieData(any(Movie.class));
        Assertions.assertThat(result).isEqualTo(result);
    }

    @DisplayName("Delete movie - positive case")
    @Test
    public void whenDeleteMovieThenNothing() {
        //given
        long movieId = 1L;
        willDoNothing().given(movieRepository).deleteById(movieId);

        //when
        movieService.deleteById(movieId);

        //then
        verify(movieRepository, times(1)).deleteById(movieId);
    }

    @DisplayName("Find movie by id - positive case")
    @Test
    public void whenFindMovieByIdThenReturnMovieDTO() {
        //given
        Movie movie = EntityGenerator.generateMovie();
        MovieDTO movieDTO = EntityGenerator.generateMovieDTO(movie);
        given(movieRepository.findByIdJoinFetchOrders(movie.getId())).willReturn(Optional.of(movie));
        given(movieMapper.mapToDTO(movie)).willReturn(movieDTO);

        //when
        MovieDTO result = movieService.findById(movie.getId());

        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(movieDTO);
    }

    @DisplayName("Find movie by id - negative case")
    @Test
    public void whenFindMovieByIdThenThrowException() {
        //given
        Movie movie = EntityGenerator.generateMovie();
        given(movieRepository.findByIdJoinFetchOrders(any())).willReturn(Optional.empty());

        //when
        assertThrows(HttpClientErrorException.class, () -> movieService.findById(movie.getId()));

        //then
        verify(movieRepository, times(1)).findByIdJoinFetchOrders(any());
    }


    @DisplayName("Find all movies - positive case")
    @Test
    public void whenFindAllMoviesThenReturnMoviesPage() {
        //given
        MovieSearchCriteria movieSearchCriteria = EntityGenerator.generateMovieSearchCriteria();
        PageRequest pageable = PageRequest.of(0, 20);
        List<Movie> movies = EntityGenerator.generateMovieList();
        given(movieRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(new PageImpl<>(movies));

        //when
        movieService.findAll(movieSearchCriteria, pageable);

        //then
        verify(movieRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
