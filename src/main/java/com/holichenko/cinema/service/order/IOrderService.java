package com.holichenko.cinema.service.order;

import com.holichenko.cinema.domain.dto.OrderDTO;
import com.holichenko.cinema.domain.dto.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    Page<OrderDTO> findAll(OrderSearchCriteria searchCriteria, Pageable pageable);

    OrderDTO findById(Long id);

    OrderDTO updateOrder(OrderDTO orderDTO, Long id);

    void deleteById(Long id);
}
