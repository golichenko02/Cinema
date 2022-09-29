package com.holichenko.cinema.util;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@UtilityClass
public class EntityGenerator {

    public static Movie generateMovie(){
        return Movie.builder()
                .id(ThreadLocalRandom.current().nextLong(50_000))
                .title(RandomStringUtils.random(30))
                .startTime(LocalDateTime.now())
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(200))
                .build();
    }

    public static MovieDTO generateMovieRequest(){
        return MovieDTO.builder()
                .title(RandomStringUtils.random(30))
                .startTime(LocalDateTime.now())
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(200))
                .build();
    }

    public static List<Movie> generateMovieList(){
        return Stream.generate(EntityGenerator::generateMovie)
                .limit(20)
                .toList();
    }

    public static MovieDTO generateMovieDTO(Movie movie){
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .startTime(movie.getStartTime())
                .hall(movie.getHall())
                .duration(movie.getDuration())
                .build();
    }
    public static List<MovieDTO> generateMovieDTOList(List<Movie> movies){
        return movies.stream()
                .map(movie -> MovieDTO.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .startTime(movie.getStartTime())
                        .hall(movie.getHall())
                        .duration(movie.getDuration())
                        .build())
                .toList();
    }

    public static MovieSearchCriteria generateMovieSearchCriteria(){
        return MovieSearchCriteria.builder()
                .title(RandomStringUtils.random(20))
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(20))
                .startTime(LocalDateTime.now())
                .build();
    }
}
