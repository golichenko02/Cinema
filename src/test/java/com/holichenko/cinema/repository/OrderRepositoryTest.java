package com.holichenko.cinema.repository;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.util.EntityGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void whenGetOrderByIdJoinFetchMovieThenReturnOrderWithMovie(){
        //given
        Movie movie = testEntityManager.persistAndFlush(EntityGenerator.generateMovie());
        Order order = testEntityManager.persistAndFlush(EntityGenerator.generateOrder(movie));

        //when
        Order result = orderRepository.findByIdJoinFetchMovie(order.getId()).get();

        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getMovie()).isNotNull();
        Assertions.assertThat(result.getMovie().getId()).isNotNull();
    }
}
