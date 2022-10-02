package com.holichenko.cinema.service.order.impl;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.domain.dto.OrderDTO;
import com.holichenko.cinema.domain.dto.OrderSearchCriteria;
import com.holichenko.cinema.domain.mapper.OrderMapper;
import com.holichenko.cinema.repository.MovieRepository;
import com.holichenko.cinema.repository.OrderRepository;
import com.holichenko.cinema.repository.specification.OrderSpecification;
import com.holichenko.cinema.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final MovieRepository movieRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecification orderSpecification;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.mapToEntity(orderDTO);
        return movieRepository.findByIdJoinFetchOrders(orderDTO.getMovieId())
                .map(movie -> {
                    movie.addOrder(order);
                    return orderRepository.saveAndFlush(order);
                })
                .map(orderMapper::mapToDTO)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Movie not found by id %s".formatted(orderDTO.getMovieId())));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> findAll(OrderSearchCriteria searchCriteria, Pageable pageable) {
        return orderRepository.findAll(orderSpecification.build(searchCriteria), pageable).map(orderMapper::mapToDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDTO findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::mapToDTO)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Order not found by id %s".formatted(id)));
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, Long id) {
        Order order = orderMapper.mapToEntity(orderDTO);
        return orderRepository.findByIdJoinFetchMovie(id)
                .map(o -> {
                    if (!Objects.equals(o.getMovie().getId(), orderDTO.getMovieId())) {
                        updateMovie(orderDTO, o);
                    }
                    return o.updateOrderData(order);
                })
                .map(orderMapper::mapToDTO)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Order not found by id %s".formatted(id)));
    }


    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    private void updateMovie(OrderDTO orderDTO, Order order) {
        Movie movie = movieRepository.findByIdJoinFetchOrders(orderDTO.getMovieId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Movie not found by id %s".formatted(orderDTO.getMovieId())));
        order.removeMovie();
        movie.addOrder(order);
    }

}
