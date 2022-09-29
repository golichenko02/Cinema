package com.holichenko.cinema.service.movie;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMovieService {

    MovieDTO createMovie(Movie movie);

    MovieDTO findById(Long id);

    Page<MovieDTO> findAll(MovieSearchCriteria searchCriteria, Pageable pageable);

    MovieDTO updateMovie(Movie movie, Long id);

    void deleteById(Long id);
}
