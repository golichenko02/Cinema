package com.holichenko.cinema.util;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import com.holichenko.cinema.domain.dto.OrderDTO;
import com.holichenko.cinema.domain.dto.OrderSearchCriteria;
import com.holichenko.cinema.domain.mapper.MovieMapper;
import com.holichenko.cinema.domain.mapper.OrderMapper;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@UtilityClass
public class EntityGenerator {

    private ModelMapper modelMapper = new ModelMapper();
    private OrderMapper orderMapper = new OrderMapper(modelMapper);
    private MovieMapper movieMapper = new MovieMapper(modelMapper, orderMapper);

    public static Movie generateMovie() {
        return Movie.builder()
                .title(RandomStringUtils.random(30))
                .startTime(LocalDateTime.now())
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(200))
                .build();
    }

    public static Movie generateMovie(MovieDTO movieDTO) {
        return Movie.builder()
                .title(movieDTO.getTitle())
                .startTime(movieDTO.getStartTime())
                .hall(movieDTO.getHall())
                .duration(movieDTO.getDuration())
                .build();
    }

    public static Movie generatePersistedMovie() {
        return Movie.builder()
                .id(ThreadLocalRandom.current().nextLong(50_000))
                .title(RandomStringUtils.random(30))
                .startTime(LocalDateTime.now())
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(200))
                .build();
    }

    public static Movie generateMovieWithOrders() {
        Movie movie = generateMovie();
        generateOrderSet(movie).forEach(movie::addOrder);
        return movie;
    }

    public static Movie generatePersistedMovieWithOrders() {
        Movie movie = generatePersistedMovie();
        generateOrderSet(movie).forEach(movie::addOrder);
        return movie;
    }

    public static Order generateOrder(Movie movie) {
        Order order = Order.builder()
                .totalSeats(ThreadLocalRandom.current().nextLong(30))
                .totalAmount(generateRandomBigDecimal())
                .build();
        movie.addOrder(order);
        return order;
    }

    public static Order generatePersistedOrder(Movie movie) {
        Order order = Order.builder()
                .id(ThreadLocalRandom.current().nextLong(50_000))
                .totalSeats(ThreadLocalRandom.current().nextLong(30))
                .totalAmount(generateRandomBigDecimal())
                .build();
        movie.addOrder(order);
        return order;
    }

    public static MovieDTO generateMovieResponse() {
        return MovieDTO.builder()
                .id(ThreadLocalRandom.current().nextLong(20_000))
                .title(RandomStringUtils.random(30))
                .startTime(LocalDateTime.now())
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(200))
                .build();
    }

    public static MovieDTO generateNonValidMovieDTO() {
        return MovieDTO.builder()
                .startTime(LocalDateTime.now())
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(200))
                .build();
    }

    public static List<Movie> generateMovieList() {
        return Stream.generate(EntityGenerator::generateMovie)
                .limit(20)
                .toList();
    }

    public static List<Movie> generatePersistedMovieList() {
        return Stream.generate(EntityGenerator::generatePersistedMovie)
                .limit(20)
                .toList();
    }

    public static List<Order> generatePersistedOrderList() {
        return Stream.generate(() -> generatePersistedOrder(EntityGenerator.generatePersistedMovie()))
                .limit(20)
                .toList();
    }

    public static Set<Order> generateOrderSet(Movie movie) {
        return Stream.generate(() -> generateOrder(movie))
                .limit(20)
                .collect(toSet());
    }

    public static MovieDTO generateMovieDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .startTime(movie.getStartTime())
                .hall(movie.getHall())
                .duration(movie.getDuration())
                .build();
    }

    public static OrderDTO generateOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .totalSeats(order.getTotalSeats())
                .totalAmount(order.getTotalAmount())
                .movieId(order.getMovie().getId())
                .build();
    }

    public static MovieSearchCriteria generateMovieSearchCriteria() {
        return MovieSearchCriteria.builder()
                .title(RandomStringUtils.random(20))
                .hall(ThreadLocalRandom.current().nextInt(20))
                .duration(ThreadLocalRandom.current().nextInt(20))
                .startTime(LocalDateTime.now())
                .build();
    }

    public static Page<MovieDTO> generatePaginatedMovies() {
        return new PageImpl<>(generatePersistedMovieList()).map(movieMapper::mapToDTO);
    }

    public static Page<OrderDTO> generatePaginatedOrders() {
        return new PageImpl<>(generatePersistedOrderList()).map(orderMapper::mapToDTO);
    }

    public static OrderDTO generateOrderResponse() {
        return OrderDTO.builder()
                .id(ThreadLocalRandom.current().nextLong(20_000))
                .totalAmount(generateRandomBigDecimal())
                .totalSeats(ThreadLocalRandom.current().nextLong(30))
                .movieId(ThreadLocalRandom.current().nextLong(1000))
                .build();
    }

    public static OrderDTO generateNonValidOrderDTO() {
        return OrderDTO.builder()
                .totalAmount(generateRandomBigDecimal())
                .totalSeats(ThreadLocalRandom.current().nextLong(30))
                .build();
    }

    public static OrderSearchCriteria generateOrderSearchCriteria() {
        return OrderSearchCriteria.builder()
                .totalAmount(generateRandomBigDecimal())
                .totalSeats(ThreadLocalRandom.current().nextLong(20))
                .movieId(ThreadLocalRandom.current().nextLong(1000))
                .build();
    }

    private static BigDecimal generateRandomBigDecimal() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(500));
    }
}
