package com.holichenko.cinema.domain.mapper;

import com.holichenko.cinema.domain.Order;
import com.holichenko.cinema.domain.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderDTO mapToDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }


    public Order mapToEntity(OrderDTO orderDTO){
        return modelMapper.map(orderDTO, Order.class);
    }
}