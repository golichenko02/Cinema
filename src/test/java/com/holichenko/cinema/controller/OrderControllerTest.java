package com.holichenko.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holichenko.cinema.domain.Movie;
import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import com.holichenko.cinema.domain.dto.OrderDTO;
import com.holichenko.cinema.domain.dto.OrderSearchCriteria;
import com.holichenko.cinema.service.order.IOrderService;
import com.holichenko.cinema.util.EntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockBean
   private IOrderService orderService;

    @DisplayName("Post order - positive case")
    @Test
    public void whenCreateOrderThenReturnOrderDTO() throws Exception {
        //given
        OrderDTO orderDTO = EntityGenerator.generateOrderResponse();
        given(orderService.createOrder(any(OrderDTO.class))).willReturn(orderDTO);

        //when //then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(OrderDTO.Fields.id).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.totalSeats).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.totalAmount).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.movieId).isNotEmpty());
    }

    @DisplayName("Post order - negative case")
    @Test
    public void whenCreateOrderWithNonValidBodyThenThrowException() throws Exception {
        //given
        OrderDTO orderDTO = EntityGenerator.generateNonValidOrderDTO();

        //when //then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[\"Movie id mustn't be null\"]"));
    }

    @DisplayName("Put order - positive case")
    @Test
    public void whenUpdateOrderThenReturnOrderDTO() throws Exception {
        //given
        OrderDTO orderDTO = EntityGenerator.generateOrderResponse();
        given(orderService.updateOrder(any(OrderDTO.class), anyLong())).willReturn(orderDTO);

        //when //then
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(OrderDTO.Fields.id).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.totalSeats).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.totalAmount).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.movieId).isNotEmpty());
    }

    @DisplayName("Put order - negative case")
    @Test
    public void whenUpdateOrderThenThrowException() throws Exception {
        //given
        OrderDTO orderDTO = EntityGenerator.generateOrderResponse();
        given(orderService.updateOrder(any(OrderDTO.class), anyLong())).willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        //when //then
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @DisplayName("Delete order - positive case")
    @Test
    public void whenDeleteOrderByIdThenNothing() throws Exception {
        //given
        doNothing().when(orderService).deleteById(anyLong());

        //when //then
        mockMvc.perform(delete("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Find order by id - positive case")
    @Test
    public void whenFindOrderByIdThenReturnOrderDTO() throws Exception {
        //given
        given(orderService.findById(anyLong())).willReturn(EntityGenerator.generateOrderResponse());

        //when //then
        mockMvc.perform(get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(OrderDTO.Fields.id).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.totalAmount).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.totalSeats).isNotEmpty())
                .andExpect(jsonPath(OrderDTO.Fields.movieId).isNotEmpty());
    }

    @DisplayName("Find order by id - negative case")
    @Test
    public void whenFindOrderByIdThenThrowException() throws Exception {
        //given
        given(orderService.findById(anyLong())).willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        //when //then
        mockMvc.perform(get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @DisplayName("Find all orders - positive case")
    @Test
    public void whenFindAllOrdersThenReturnPaginatedOrderDTOList() throws Exception {
        //given
        OrderSearchCriteria searchCriteria = EntityGenerator.generateOrderSearchCriteria();
        given(orderService.findAll(any(OrderSearchCriteria.class), any(Pageable.class))).willReturn(EntityGenerator.generatePaginatedOrders());

        //when //then
        mockMvc.perform(post("/orders/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchCriteria)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content").hasJsonPath())
                .andExpect(jsonPath("content", hasSize(20)))
                .andExpect(jsonPath("numberOfElements").value(20))
                .andExpect(jsonPath("totalPages").value(1));
    }

}
