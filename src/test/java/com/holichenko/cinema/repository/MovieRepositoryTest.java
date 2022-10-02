package com.holichenko.cinema.repository;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.util.EntityGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void whenGetMovieByIdJoinFetchOrdersThenReturnMovieWithOrders(){
        //given
        Movie movie = testEntityManager.persistAndFlush(EntityGenerator.generateMovieWithOrders());

        //when
        Movie result = movieRepository.findByIdJoinFetchOrders(movie.getId()).get();

        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getOrders()).isNotEmpty();
    }
}
