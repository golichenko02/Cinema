package com.holichenko.cinema.service;

import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.domain.dto.OrderDTO;
import com.holichenko.cinema.domain.dto.OrderSearchCriteria;
import com.holichenko.cinema.domain.mapper.OrderMapper;
import com.holichenko.cinema.repository.MovieRepository;
import com.holichenko.cinema.repository.OrderRepository;
import com.holichenko.cinema.repository.specification.OrderSpecification;
import com.holichenko.cinema.service.order.impl.OrderService;
import com.holichenko.cinema.util.EntityGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private OrderSpecification movieSpecification;

    @InjectMocks
    private OrderService orderService;


    @DisplayName("Save order - positive case")
    @Test
    public void whenCreateOrderThenReturnOrderDTO() {
        //given
        Movie givenMovie = EntityGenerator.generateMovieWithOrders();
        Order givenOrder = EntityGenerator.generateOrder(givenMovie);
        OrderDTO givenOrderDTO = EntityGenerator.generateOrderDTO(givenOrder);

        given(orderMapper.mapToEntity(givenOrderDTO)).willReturn(givenOrder);
        given(movieRepository.findByIdJoinFetchOrders(anyLong())).willReturn(Optional.ofNullable(givenMovie));
        given(orderRepository.saveAndFlush(any(Order.class))).willReturn(givenOrder);
        given(orderMapper.mapToDTO(givenOrder)).willReturn(givenOrderDTO);

        //when
        OrderDTO result = orderService.createOrder(givenOrderDTO);

        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getMovieId()).isEqualTo(givenMovie.getId());
        verify(orderRepository, times(1)).saveAndFlush(any());
        verify(movieRepository, times(1)).findByIdJoinFetchOrders(any());
    }

    @DisplayName("Save order - negative case")
    @Test
    public void whenCreateOrderThenThrowException() {
        //given
        Movie givenMovie = EntityGenerator.generateMovieWithOrders();
        Order givenOrder = EntityGenerator.generateOrder(givenMovie);
        OrderDTO givenOrderDTO = EntityGenerator.generateOrderDTO(givenOrder);

        given(orderMapper.mapToEntity(givenOrderDTO)).willReturn(givenOrder);
        given(movieRepository.findByIdJoinFetchOrders(anyLong())).willReturn(Optional.empty());

        //when
        assertThrows(HttpClientErrorException.class, () -> orderService.createOrder(givenOrderDTO));

        //then
        verify(movieRepository, times(1)).findByIdJoinFetchOrders(any());
    }

    @DisplayName("Update order - negative case")
    @Test
    public void whenUpdateOrderThenThrowException() {
        //given
        Movie givenMovie = EntityGenerator.generateMovieWithOrders();
        Order givenOrder = EntityGenerator.generateOrder(givenMovie);
        OrderDTO givenOrderDTO = EntityGenerator.generateOrderDTO(givenOrder);

        given(orderMapper.mapToEntity(givenOrderDTO)).willReturn(givenOrder);
        given(orderRepository.findByIdJoinFetchMovie(anyLong())).willReturn(Optional.empty());

        //when
        assertThrows(HttpClientErrorException.class, () -> orderService.updateOrder(givenOrderDTO, anyLong()));

        //then
        verify(orderRepository, times(1)).findByIdJoinFetchMovie(any());
    }

    @DisplayName("Update order - positive case")
    @Test
    public void whenUpdateOrderThenReturnOrderDTO() {
        //given
        Movie givenMovie = EntityGenerator.generateMovieWithOrders();
        Order givenOrder = spy(EntityGenerator.generateOrder(givenMovie));
        OrderDTO givenOrderDTO = EntityGenerator.generateOrderDTO(givenOrder);

        given(orderMapper.mapToEntity(givenOrderDTO)).willReturn(givenOrder);
        given(orderRepository.findByIdJoinFetchMovie(anyLong())).willReturn(Optional.of(givenOrder));
        given(orderMapper.mapToDTO(givenOrder)).willReturn(givenOrderDTO);

        //when
        OrderDTO result = orderService.updateOrder(givenOrderDTO, anyLong());

        //then
        verify(orderRepository, times(1)).findByIdJoinFetchMovie(anyLong());
        verify(givenOrder, times(1)).updateOrderData(any(Order.class));
        Assertions.assertThat(result).isEqualTo(givenOrderDTO);
    }


    @DisplayName("Find order by id - positive case")
    @Test
    public void whenFindOrderByIdThenReturnOrderDTO() {
        //given
        Movie givenMovie = EntityGenerator.generateMovieWithOrders();
        Order givenOrder = spy(EntityGenerator.generateOrder(givenMovie));
        OrderDTO givenOrderDTO = EntityGenerator.generateOrderDTO(givenOrder);

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(givenOrder));
        given(orderMapper.mapToDTO(givenOrder)).willReturn(givenOrderDTO);

        //when
        OrderDTO result = orderService.findById(anyLong());

        //then
        verify(orderRepository, times(1)).findById(anyLong());
        Assertions.assertThat(result).isEqualTo(givenOrderDTO);
    }

    @DisplayName("Find order by id - negative case")
    @Test
    public void whenFindOrderByIdThenThrowException() {
        //given
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        assertThrows(HttpClientErrorException.class, () -> orderService.findById(anyLong()));

        //then
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @DisplayName("Find all orders - positive case")
    @Test
    public void whenFindAllMoviesThenReturnMoviesPage() {
        //given
        OrderSearchCriteria searchCriteria = new OrderSearchCriteria();
        PageRequest pageable = PageRequest.of(0, 20);

        given(orderRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(new PageImpl<>(Collections.emptyList()));

        //when
        orderService.findAll(searchCriteria, pageable);

        //then
        verify(orderRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
