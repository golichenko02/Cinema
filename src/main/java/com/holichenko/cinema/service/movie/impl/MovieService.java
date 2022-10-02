package com.holichenko.cinema.service.movie.impl;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import com.holichenko.cinema.domain.mapper.MovieMapper;
import com.holichenko.cinema.repository.MovieRepository;
import com.holichenko.cinema.repository.specification.MovieSpecification;
import com.holichenko.cinema.service.movie.IMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieSpecification movieSpecification;

    @Override
    public MovieDTO createMovie(Movie movie) {
        return movieMapper.mapToDTO(movieRepository.save(movie));
    }

    @Transactional(readOnly = true)
    @Override
    public MovieDTO findById(Long id) {
        return movieRepository.findByIdJoinFetchOrders(id)
                .map(movieMapper::mapToDTO)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Movie not found by id %s".formatted(id)));
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(MovieSearchCriteria searchCriteria, Pageable pageable) {
        return movieRepository.findAll(movieSpecification.build(searchCriteria), pageable).map(movieMapper::mapToDTO);
    }

    @Override
    public MovieDTO updateMovie(Movie movie, Long id) {
        return movieRepository.findByIdJoinFetchOrders(id)
                .map(m -> m.updateMovieData(movie))
                .map(movieMapper::mapToDTO)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Movie not found by id %s".formatted(id)));
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}
