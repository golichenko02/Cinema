package com.holichenko.cinema.domain.mapper;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieMapper {

    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;

    public MovieDTO mapToDTO(Movie movie) {
        return Optional.ofNullable(movie)
                .map(m ->  modelMapper.map(m, MovieDTO.class))
                .orElse(null);
    }


    public Movie mapToEntity(MovieDTO movieDTO) {
        return modelMapper.map(movieDTO, Movie.class);
    }
}
