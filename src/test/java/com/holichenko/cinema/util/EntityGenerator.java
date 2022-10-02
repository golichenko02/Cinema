package com.holichenko.cinema.util;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import com.holichenko.cinema.domain.dto.OrderDTO;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

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

    public static Movie generateMovieWithOrders(){
        Movie movie = generateMovie();
        generateOrderSet(movie).forEach(movie::addOrder);
        return movie;
    }

    public static Order generateOrder(Movie movie){
        return Order.builder()
                .id(ThreadLocalRandom.current().nextLong(50_000))
                .totalSeats(ThreadLocalRandom.current().nextLong(30))
                .totalAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(500)))
                .movie(movie)
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

    public static Set<Order> generateOrderSet(Movie movie){
        return Stream.generate(() -> generateOrder(movie))
                .limit(20)
                .collect(toSet());
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

    public static OrderDTO generateOrderDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .totalSeats(order.getTotalSeats())
                .totalAmount(order.getTotalAmount())
                .movieId(order.getMovie().getId())
                .build();
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
